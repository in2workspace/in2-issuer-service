package es.in2.issuer.backoffice.util.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.LEARCredentialMachine;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static es.in2.issuer.backoffice.util.Utils.parseIsoZonedDataTimeToUnixTimestamp;

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

        Instant currentTime = Instant.now();

        LEARCredentialMachine.CredentialSubject learCredentialMachineCredentialSubject = LEARCredentialMachine.CredentialSubject.builder()
                .mandate(LEARCredentialMachine.CredentialSubject.Mandate.builder()
                        .id(mandate.id())
                        .lifeSpan(LEARCredentialMachine.CredentialSubject.Mandate.LifeSpan.builder()
                                .startDateTime(currentTime.toString())
                                .endDateTime(currentTime.plus(30, ChronoUnit.DAYS).toString()) // tod: credential expiration from config
                                .build())
                        .mandator(mandate.mandator())
                        .mandatee(mandate.mandatee())
                        .power(powerList)
                        .signer(mandate.signer())
                        .build())
                .build();

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
                .notValidBefore(parseIsoZonedDataTimeToUnixTimestamp(credentialData.validFrom()))
                .issuer(credentialData.issuer())
                .expirationTime(parseIsoZonedDataTimeToUnixTimestamp(credentialData.expirationDate()))
                .issuedAt(parseIsoZonedDataTimeToUnixTimestamp(credentialData.issuanceDate()))
                .verifiableCredential(credentialData)
                .jwtId(UUID.randomUUID().toString())
                .build();
    }

}
