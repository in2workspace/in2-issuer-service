package es.in2.issuer.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.Payload;
import es.in2.issuer.model.dto.Credential;
import es.in2.issuer.model.dto.VerifiableCertification;
import es.in2.issuer.model.enums.SupportedCredentialTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerifiableCertificationFactory {
    static private final String VERIFIABLE_CREDENTIAL_TYPE = "VerifiableCredential";

    private final ObjectMapper objectMapper;

    //private final AppConfiguration appConfiguration; //obtener el default signer de configs

    public Credential createCredential(Payload payload) {
        // 1. Map payload into VerifiableCertificationCredentialSubject
        // 1.1 generate IDs for compliances
        // 1.2 Map CredentialSubject and compliances into final Credential

        VerifiableCertification verifiableCertificationData = objectMapper.convertValue(payload, VerifiableCertification.class);

        // Compliance list with new IDs
        List<VerifiableCertification.CredentialSubject.Compliance> complianceList = verifiableCertificationData.credentialSubject().compliance().stream()
                .map(compliance -> VerifiableCertification.CredentialSubject.Compliance.builder()
                        .id(UUID.randomUUID().toString())
                        .scope(compliance.scope())
                        .standard(compliance.standard())
                        .build())
                .toList();

        // Create the Signer object using the retrieved UserDetails
        VerifiableCertification.Signer signer = VerifiableCertification.Signer.builder()
                .commonName("appConfiguration.getSignerCommonName()") // obtener el default signer de configs
                .country("appConfiguration.getSignerCountry()")
                .emailAddress("appConfiguration.getSignerEmail()")
                .organization("appConfiguration.getSignerOrganization()")
                .organizationIdentifier("appConfiguration.getSignerOrganizationIdentifier()")
                .serialNumber("appConfiguration.getSignerSerialNumber()")
                .build();

        // Build the final VerifiableCertificationData object
        VerifiableCertification finalVerifiableCertificationData = VerifiableCertification.builder()
                .context(List.of("https://www.w3.org/ns/credentials/v2", "https://dome-marketplace.eu/2022/credentials/learcredential/v1"))
                .id(UUID.randomUUID().toString())
                .type(List.of(
                        SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue(),
                        VERIFIABLE_CREDENTIAL_TYPE))
                .issuer(verifiableCertificationData.issuer())
                .credentialSubject(VerifiableCertification.CredentialSubject.builder()
                        .company(verifiableCertificationData.credentialSubject().company())
                        .product(verifiableCertificationData.credentialSubject().product())
                        .compliance(complianceList)
                        .build())
                .issuanceDate(verifiableCertificationData.issuanceDate())
                .validFrom(verifiableCertificationData.validFrom())
                .expirationDate(verifiableCertificationData.expirationDate())
                .signer(signer)
                .build();

        return Credential.builder()
                .notValidBefore(parseDateToUnixTime(finalVerifiableCertificationData.validFrom()))
                .issuer(finalVerifiableCertificationData.issuer().id())
                .expirationTime(parseDateToUnixTime(finalVerifiableCertificationData.expirationDate()))
                .issuedAt(parseDateToUnixTime(finalVerifiableCertificationData.issuanceDate()))
                .verifiableCredential(finalVerifiableCertificationData)
                .jwtId(UUID.randomUUID().toString())
                .build();
    }

    private long parseDateToUnixTime(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return zonedDateTime.toInstant().getEpochSecond();
    }
}
