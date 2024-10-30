package es.in2.issuer.backoffice.util.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.Payload;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.LEARCredentialMachine;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
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
public class LEARCredentialMachineFactory {

    private static final String VERIFIABLE_CREDENTIAL_TYPE = "VerifiableCredential";

    private final ObjectMapper objectMapper;

    public Credential createCredential(Payload payload) {

        LEARCredentialMachine.CredentialSubject.Mandate mandate = objectMapper.convertValue(payload, LEARCredentialMachine.CredentialSubject.Mandate.class);

        List<LEARCredentialMachine.CredentialSubject.Mandate.Power> powerList = mandate.power().stream()
                .map(power -> LEARCredentialMachine.CredentialSubject.Mandate.Power.builder()
                        .id(UUID.randomUUID().toString())
                        .tmfType(power.tmfType())
                        .tmfAction(power.tmfAction())
                        .tmfDomain(power.tmfDomain())
                        .tmfFunction(power.tmfFunction())
                        .build())
                .toList();

        LEARCredentialMachine.CredentialSubject learCredentialMachineCredentialSubject = LEARCredentialMachine.CredentialSubject.builder()
                .mandate(LEARCredentialMachine.CredentialSubject.Mandate.builder()
                        .id(mandate.id())
                        .lifeSpan(mandate.lifeSpan())
                        .mandator(mandate.mandator())
                        .mandatee(mandate.mandatee())
                        .power(powerList)
                        .signer(mandate.signer())
                        .build())
                .build();

        Instant currentTime = Instant.now();

        LEARCredentialMachine credentialData = LEARCredentialMachine.builder()
                .context(List.of("https://www.w3.org/ns/credentials/v2", "https://dome-marketplace.eu/2022/credentials/learcredential/v1"))
                .id(UUID.randomUUID().toString())
                .expirationDate(currentTime.plus(30, ChronoUnit.DAYS).toString()) // tod: credential expiration from config
                .issuanceDate(currentTime.toString())
                .validFrom(currentTime.toString())
                .type(List.of(
                        SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue(),
                        VERIFIABLE_CREDENTIAL_TYPE))
                .issuer("did:elsi" + mandate.mandator().organizationIdentifier())
                .credentialSubject(learCredentialMachineCredentialSubject)
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
