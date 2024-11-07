package es.in2.issuer.backoffice.util.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.LEARCredentialEmployee;
import es.in2.issuer.backoffice.model.enums.DidMethods;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static es.in2.issuer.backoffice.util.ApplicationUtils.parseIsoZonedDataTimeToUnixTimestamp;

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
                        .tmfDomain(power.tmfDomain())
                        .tmfFunction(power.tmfFunction())   
                        .tmfAction(power.tmfAction())
                        .build())
                .toList();

        Instant currentTime = Instant.now();

        LEARCredentialEmployee.CredentialSubject learCredentialEmployeeCredentialSubject = LEARCredentialEmployee.CredentialSubject.builder()
                .mandate(LEARCredentialEmployee.CredentialSubject.Mandate.builder()
                        .id(UUID.randomUUID().toString())
                        .lifeSpan(LEARCredentialEmployee.CredentialSubject.Mandate.LifeSpan.builder()
                                .startDateTime(currentTime.toString())
                                .endDateTime(currentTime.plus(365, ChronoUnit.DAYS).toString())
                                .build())
                        .mandator(mandate.mandator())
                        .mandatee(mandate.mandatee())
                        .power(powerList)
                        .signer(mandate.signer())
                        .build())
                .build();

        LEARCredentialEmployee learCredentialEmployee = LEARCredentialEmployee.builder()
                .context(List.of("https://www.w3.org/ns/credentials/v2", "https://dome-marketplace.eu/2022/credentials/learcredential/v1"))
                .id(UUID.randomUUID().toString())
                .expirationDate(currentTime.plus(365, ChronoUnit.DAYS).toString())
                .issuanceDate(currentTime.toString())
                .validFrom(currentTime.toString())
                .type(List.of(
                        SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue(),
                        VERIFIABLE_CREDENTIAL_TYPE))
                .issuer(DidMethods.DID_ELSI.getName() + mandate.mandator().organizationIdentifier())
                .credentialSubject(learCredentialEmployeeCredentialSubject)
                .build();

        return Credential.builder()
                .notValidBefore(parseIsoZonedDataTimeToUnixTimestamp(learCredentialEmployee.validFrom()))
                .issuer(learCredentialEmployee.issuer())
                .expirationTime(parseIsoZonedDataTimeToUnixTimestamp(learCredentialEmployee.expirationDate()))
                .issuedAt(parseIsoZonedDataTimeToUnixTimestamp(learCredentialEmployee.issuanceDate()))
                .verifiableCredential(learCredentialEmployee)
                .jwtId(UUID.randomUUID().toString())
                .build();
    }

}
