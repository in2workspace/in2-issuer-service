package es.in2.issuer.backoffice.util.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.LEARCredentialEmployee;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LEARCredentialEmployeeFactory {

    private static final String VERIFIABLE_CREDENTIAL_TYPE = "VerifiableCredential";
    
    private final ObjectMapper objectMapper;

    public Credential createCredential(Payload payload) {

        LEARCredentialEmployee.CredentialSubject.Mandate mandate = objectMapper.convertValue(payload, LEARCredentialEmployee.CredentialSubject.Mandate.class);

        List<LEARCredentialEmployee.CredentialSubject.Mandate.Power> powerList = mandate.power().stream()
                .map(power -> LEARCredentialEmployee.CredentialSubject.Mandate.Power.builder()
                        .id(UUID.randomUUID().toString())
                        .tmfType(power.tmfType())
                        .tmfAction(power.tmfAction())
                        .tmfDomain(power.tmfDomain())
                        .tmfFunction(power.tmfFunction())
                        .build())
                .toList();

        Instant currentTime = Instant.now();

        LEARCredentialEmployee.CredentialSubject learCredentialEmployeeCredentialSubject = LEARCredentialEmployee.CredentialSubject.builder()
                .mandate(LEARCredentialEmployee.CredentialSubject.Mandate.builder()
                        .id(UUID.randomUUID().toString())
                        .lifeSpan(LEARCredentialEmployee.CredentialSubject.Mandate.LifeSpan.builder()
                                .startDateTime(currentTime.toString())
                                .endDateTime(currentTime.plus(30, ChronoUnit.DAYS).toString()) // tod: credential expiration from config
                                .build())
                        .mandator(mandate.mandator())
                        .mandatee(mandate.mandatee())
                        .power(powerList)
                        .signer(mandate.signer())
                        .build())
                .build();

        LEARCredentialEmployee credentialData = LEARCredentialEmployee.builder()
                .context(List.of("https://www.w3.org/ns/credentials/v2", "https://dome-marketplace.eu/2022/credentials/learcredential/v1"))
                .id(UUID.randomUUID().toString())
                .expirationDate(currentTime.plus(30, ChronoUnit.DAYS).toString()) // tod: credential expiration from config
                .issuanceDate(currentTime.toString())
                .validFrom(currentTime.toString())
                .type(List.of(
                        SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue(), 
                        VERIFIABLE_CREDENTIAL_TYPE))
                .issuer("did:elsi" + mandate.mandator().organizationIdentifier())
                .credentialSubject(learCredentialEmployeeCredentialSubject)
                .build();

        return Credential.builder()
                .notValidBefore(parseDateToUnixTime(credentialData.validFrom()))
                .issuer(credentialData.issuer())
                .expirationTime(parseDateToUnixTime(credentialData.expirationDate()))
                .issuedAt(parseDateToUnixTime(credentialData.issuanceDate()))
                .verifiableCredential(credentialData)
                .jwtId(UUID.randomUUID().toString())
                .build();
    }

    private long parseDateToUnixTime(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return zonedDateTime.toInstant().getEpochSecond();
    }
    
}
