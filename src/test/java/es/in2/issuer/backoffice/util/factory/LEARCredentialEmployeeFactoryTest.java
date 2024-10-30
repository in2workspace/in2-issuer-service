package es.in2.issuer.backoffice.util.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.LEARCredentialEmployee;
import es.in2.issuer.backoffice.model.dto.LEARCredentialEmployee.CredentialSubject.Mandate;
import es.in2.issuer.backoffice.model.dto.LEARCredentialEmployee.CredentialSubject.Mandate.Power;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LEARCredentialEmployeeFactoryTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LEARCredentialEmployeeFactory factory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCredential_ShouldReturnValidCredential() {
        // Mock Payload to Mandate
        Mandate mandateMock = Mandate.builder()
                .id(null)
                .mandatee(Mandate.Mandatee.builder()
                        .email("jhon@organization.com")
                        .firstName("Jhon")
                        .lastName("Doe")
                        .mobilePhone("+34666666666")
                        .build())
                .mandator(Mandate.Mandator.builder()
                        .organizationIdentifier("org-1234")
                        .commonName("Dave Foe")
                        .organization("Organization Inc.")
                        .emailAddress("info@organization.com")
                        .serialNumber("S-12345")
                        .country("ES")
                        .build())
                .signer(Mandate.Signer.builder()
                        .organizationIdentifier("s-org-4321")
                        .commonName("Name Lastname")
                        .organization("Signer Org")
                        .emailAddress("info@signer.com")
                        .serialNumber("S-54321")
                        .country("ES")
                        .build())
                .power(List.of(Power.builder()
                        .id("powerId")
                        .tmfType("type")
                        .tmfAction("action")
                        .tmfDomain("domain")
                        .tmfFunction("function")
                        .build()))
                .build();

        when(objectMapper.convertValue(any(Payload.class), Mockito.eq(Mandate.class)))
                .thenReturn(mandateMock);

        // Call the method under test
        Payload payload = Mockito.mock(Payload.class);
        Credential credential = factory.createCredential(payload);

        // Validate Credential contents
        assertNotNull(credential);
        assertEquals("did:elsi" + mandateMock.mandator().organizationIdentifier(), credential.issuer());
        assertEquals(SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue(),
                ((LEARCredentialEmployee) credential.verifiableCredential()).type().get(0));

        // Check expiration and issuance dates within expected time range
        Instant now = Instant.now();
        assertTrue(credential.issuedAt() <= now.getEpochSecond());
        assertTrue(credential.expirationTime() >= now.plus(29, ChronoUnit.DAYS).getEpochSecond());

    }
}