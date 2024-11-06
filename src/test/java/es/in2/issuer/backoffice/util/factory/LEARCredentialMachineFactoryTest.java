package es.in2.issuer.backoffice.util.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.LEARCredentialMachine;
import es.in2.issuer.backoffice.model.enums.DidMethods;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import jakarta.validation.Payload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LEARCredentialMachineFactoryTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LEARCredentialMachineFactory factory;

    @Test
    void createCredential_ShouldReturnValidCredential() {
        // Mock Payload to Mandate
        LEARCredentialMachine.CredentialSubject.Mandate mandateMock = LEARCredentialMachine.CredentialSubject.Mandate.builder()
                .id(null)
                .mandatee(LEARCredentialMachine.CredentialSubject.Mandate.Mandatee.builder()
                        .contact(LEARCredentialMachine.CredentialSubject.Mandate.Mandatee.Contact.builder().email("email@email.com").phone("12345").build())
                        .description("description")
                        .domain("domain")
                        .serviceName("service name")
                        .ipAddress("127.0.0.1")
                        .version("v1.0.0")
                        .build())
                .mandator(LEARCredentialMachine.CredentialSubject.Mandate.Mandator.builder()
                        .organizationIdentifier("org-1234")
                        .commonName("Dave Foe")
                        .organization("Organization Inc.")
                        .emailAddress("info@organization.com")
                        .serialNumber("S-12345")
                        .country("ES")
                        .build())
                .signer(LEARCredentialMachine.CredentialSubject.Mandate.Signer.builder()
                        .organizationIdentifier("s-org-4321")
                        .commonName("Name Lastname")
                        .organization("Signer Org")
                        .emailAddress("info@signer.com")
                        .serialNumber("S-54321")
                        .country("ES")
                        .build())
                .power(List.of(LEARCredentialMachine.CredentialSubject.Mandate.Power.builder()
                        .id("powerId")
                        .tmfType("type")
                        .tmfAction("action")
                        .tmfDomain("domain")
                        .tmfFunction("function")
                        .build()))
                .build();

        when(objectMapper.convertValue(any(Payload.class), Mockito.eq(LEARCredentialMachine.CredentialSubject.Mandate.class)))
                .thenReturn(mandateMock);

        // Call the method under test
        Payload payload = Mockito.mock(Payload.class);
        Credential credential = factory.createCredential(payload);
        LEARCredentialMachine learCredentialMachine = (LEARCredentialMachine) credential.verifiableCredential();

        // Validate Credential contents
        assertNotNull(credential);
        assertEquals(DidMethods.DID_ELSI.getValue() + mandateMock.mandator().organizationIdentifier(), credential.issuer());
        assertEquals("service name", learCredentialMachine.credentialSubject().mandate().mandatee().serviceName());
        assertEquals(SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue(), learCredentialMachine.type().get(0));

        // Check expiration and issuance dates within expected time range
        Instant now = Instant.now();
        assertTrue(credential.issuedAt() <= now.getEpochSecond());
        assertTrue(credential.expirationTime() >= now.plus(365, ChronoUnit.DAYS).getEpochSecond());

    }
}