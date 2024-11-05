package es.in2.issuer.backoffice.util.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.VerifiableCertification;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import jakarta.validation.Payload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifiableCertificationFactoryTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private VerifiableCertificationFactory factory;

    @Test
    void createCredential_ShouldReturnValidCredential() {

        // Mock Payload to VerifiableCertification

        VerifiableCertification verifiableCertificationMock = VerifiableCertification.builder()
                .id(null)
                .issuer(VerifiableCertification.Issuer.builder()
                        .id("id-1234")
                        .organization("Organization")
                        .commonName("Common Name")
                        .country("ES")
                        .build())
                .credentialSubject(VerifiableCertification.CredentialSubject.builder()
                        .company(VerifiableCertification.CredentialSubject.Company.builder()
                                .id("id-1111")
                                .address("address")
                                .email("email@email.com")
                                .commonName("Common Name")
                                .organization("Organization")
                                .country("ES")
                                .build())
                        .compliance(Collections.singletonList(VerifiableCertification.CredentialSubject.Compliance.builder()
                                .scope("scope")
                                .standard("standard")
                                .build()))
                        .product(VerifiableCertification.CredentialSubject.Product.builder()
                                .productId("productId")
                                .productName("product name")
                                .productVersion("v1.0.0")
                                .build())
                        .build())
                .validFrom("2024-08-22T00:00:00Z")
                .issuanceDate("2024-08-22T00:00:00Z")
                .expirationDate("2024-09-22T00:00:00Z")
                .build();

        when(objectMapper.convertValue(any(Payload.class), Mockito.eq(VerifiableCertification.class)))
                .thenReturn(verifiableCertificationMock);

        // Call the method under test
        Payload payload = Mockito.mock(Payload.class);
        Credential credential = factory.createCredential(payload);
        VerifiableCertification verifiableCertification = (VerifiableCertification) credential.verifiableCredential();

        // Validate Credential contents
        assertNotNull(credential);
        assertEquals("id-1234", credential.issuer());
        assertEquals("productId", verifiableCertification.credentialSubject().product().productId());
        assertEquals(SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue(), verifiableCertification.type().get(0));

        // Check expiration and issuance dates within expected time range
        assertTrue(credential.issuedAt() <= Instant.parse("2024-08-22T00:00:00Z").getEpochSecond());
        assertTrue(credential.expirationTime() >= Instant.parse("2024-09-22T00:00:00Z").getEpochSecond());

    }
}