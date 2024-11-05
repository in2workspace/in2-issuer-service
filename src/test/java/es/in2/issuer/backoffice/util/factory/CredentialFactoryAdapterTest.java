package es.in2.issuer.backoffice.util.factory;

import es.in2.issuer.backoffice.exception.UnsupportedSchemaException;
import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.CredentialRequest;
import es.in2.issuer.backoffice.model.enums.CredentialFormat;
import es.in2.issuer.backoffice.model.enums.OperationMode;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import jakarta.validation.Payload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CredentialFactoryAdapterTest {

    @Mock
    private LEARCredentialEmployeeFactory learCredentialEmployeeFactory;

    @Mock
    private LEARCredentialMachineFactory learCredentialMachineFactory;

    @Mock
    private VerifiableCertificationFactory verifiableCertificationFactory;

    @InjectMocks
    private CredentialFactoryAdapter credentialFactoryAdapter;

    @Test
    void testCreateCredentialForLEARCredentialEmployee() {
        // Arrange
        Payload mockPayload = mock(Payload.class);
        CredentialRequest request = new CredentialRequest(
                SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue(),
                CredentialFormat.JWT_VC_JSON,
                mockPayload,
                OperationMode.S,
                30,
                "http://response.uri"
        );
        Credential mockCredential = Credential.builder().build();
        when(learCredentialEmployeeFactory.createCredential(mockPayload)).thenReturn(mockCredential);

        // Act
        Credential result = credentialFactoryAdapter.createCredential(request);

        // Assert
        assertNotNull(result);
        assertEquals(mockCredential, result);
        verify(learCredentialEmployeeFactory, times(1)).createCredential(mockPayload);
    }

    @Test
    void testCreateCredentialForLEARCredentialMachine() {
        // Arrange
        Payload mockPayload = mock(Payload.class);
        CredentialRequest request = new CredentialRequest(
                SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue(),
                CredentialFormat.JWT_VC_JSON,
                mockPayload,
                OperationMode.S,
                30,
                "http://response.uri"
        );
        Credential mockCredential = Credential.builder().build();
        when(learCredentialMachineFactory.createCredential(mockPayload)).thenReturn(mockCredential);

        // Act
        Credential result = credentialFactoryAdapter.createCredential(request);

        // Assert
        assertNotNull(result);
        assertEquals(mockCredential, result);
        verify(learCredentialMachineFactory, times(1)).createCredential(mockPayload);
    }

    @Test
    void testCreateCredentialForVerifiableCertification() {
        // Arrange
        Payload mockPayload = mock(Payload.class);
        CredentialRequest request = new CredentialRequest(
                SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue(),
                CredentialFormat.JWT_VC_JSON,
                mockPayload,
                OperationMode.S,
                30,
                "http://response.uri"
        );
        Credential mockCredential = Credential.builder().build();
        when(verifiableCertificationFactory.createCredential(mockPayload)).thenReturn(mockCredential);

        // Act
        Credential result = credentialFactoryAdapter.createCredential(request);

        // Assert
        assertNotNull(result);
        assertEquals(mockCredential, result);
        verify(verifiableCertificationFactory, times(1)).createCredential(mockPayload);
    }

    @Test
    void testCreateCredentialWithUnsupportedSchema() {
        // Arrange
        Payload mockPayload = mock(Payload.class);
        CredentialRequest request = new CredentialRequest(
                "UnsupportedSchema",
                CredentialFormat.JWT_VC_JSON,
                mockPayload,
                OperationMode.S,
                30,
                "http://response.uri"
        );

        // Act & Assert
        UnsupportedSchemaException thrown = assertThrows(UnsupportedSchemaException.class, () -> {
            credentialFactoryAdapter.createCredential(request);
        });
        assertEquals("Schema: UnsupportedSchema is not supported", thrown.getMessage());
    }

}