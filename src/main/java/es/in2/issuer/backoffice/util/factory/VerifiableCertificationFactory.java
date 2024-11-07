package es.in2.issuer.backoffice.util.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.VerifiableCertification;
import es.in2.issuer.backoffice.model.enums.DidMethods;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static es.in2.issuer.backoffice.util.ApplicationUtils.parseIsoZonedDataTimeToUnixTimestamp;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerifiableCertificationFactory {

    private static final String VERIFIABLE_CREDENTIAL_TYPE = "VerifiableCredential";

    private final ObjectMapper objectMapper;

    //private final AppConfiguration appConfiguration; //obtener el default signer de configs

    public Credential createCredential(Payload payload) {

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
                .context(List.of("https://www.w3.org/ns/credentials/v2", "https://trust-framework.dome-marketplace.eu/credentials/verifiablecertification/v1"))
                .id(UUID.randomUUID().toString())
                .type(List.of(
                        SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue(),
                        VERIFIABLE_CREDENTIAL_TYPE))
                .issuer(VerifiableCertification.Issuer.builder()
                        .id(DidMethods.DID_ELSI.getName() + signer.organizationIdentifier())
                        .country(signer.country())
                        .commonName(signer.commonName())
                        .organization(signer.organization())
                        .build())
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
                .notValidBefore(parseIsoZonedDataTimeToUnixTimestamp(finalVerifiableCertificationData.validFrom()))
                .issuer(finalVerifiableCertificationData.issuer().id())
                .expirationTime(parseIsoZonedDataTimeToUnixTimestamp(finalVerifiableCertificationData.expirationDate()))
                .issuedAt(parseIsoZonedDataTimeToUnixTimestamp(finalVerifiableCertificationData.issuanceDate()))
                .verifiableCredential(finalVerifiableCertificationData)
                .jwtId(UUID.randomUUID().toString())
                .build();
    }

}
