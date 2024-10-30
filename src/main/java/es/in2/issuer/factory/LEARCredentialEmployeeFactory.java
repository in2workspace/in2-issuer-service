package es.in2.issuer.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.Payload;
import es.in2.issuer.model.dto.Credential;
import es.in2.issuer.model.dto.LEARVerifiableCredentialEmployee;
import es.in2.issuer.model.enums.SupportedCredentialTypes;
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
    
    static private final String VERIFIABLE_CREDENTIAL_TYPE = "VerifiableCredential";
    
    private final ObjectMapper objectMapper;

    public Credential createCredential(Payload payload) {

        LEARVerifiableCredentialEmployee.CredentialSubject.Mandate mandate = objectMapper.convertValue(payload, LEARVerifiableCredentialEmployee.CredentialSubject.Mandate.class);

        List<LEARVerifiableCredentialEmployee.CredentialSubject.Mandate.Power> powerList = mandate.power().stream()
                .map(power -> LEARVerifiableCredentialEmployee.CredentialSubject.Mandate.Power.builder()
                        .id(UUID.randomUUID().toString())
                        .tmfType(power.tmfType())
                        .tmfAction(power.tmfAction())
                        .tmfDomain(power.tmfDomain())
                        .tmfFunction(power.tmfFunction())
                        .build())
                .toList();

        LEARVerifiableCredentialEmployee.CredentialSubject learCredentialEmployeeCredentialSubject = LEARVerifiableCredentialEmployee.CredentialSubject.builder()
                .mandate(LEARVerifiableCredentialEmployee.CredentialSubject.Mandate.builder()
                        .id(mandate.id())
                        .lifeSpan(mandate.lifeSpan())
                        .mandator(mandate.mandator())
                        .mandatee(mandate.mandatee())
                        .power(powerList)
                        .signer(mandate.signer())
                        .build())
                .build();

        Instant currentTime = Instant.now();

        LEARVerifiableCredentialEmployee credentialData = LEARVerifiableCredentialEmployee.builder()
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
                .credentialData(credentialData)
                .jwtId(UUID.randomUUID().toString())
                .build();
    }

    private long parseDateToUnixTime(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return zonedDateTime.toInstant().getEpochSecond();
    }
    
}