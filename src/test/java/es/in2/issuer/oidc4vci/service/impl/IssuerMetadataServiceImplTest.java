package es.in2.issuer.oidc4vci.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import es.in2.issuer.backoffice.model.enums.CredentialFormat;
import es.in2.issuer.backoffice.model.enums.DidMethods;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import es.in2.issuer.oidc4vci.model.enums.SupportedSingingAlgorithms;
import es.in2.issuer.oidc4vci.model.dto.IssuerMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IssuerMetadataServiceImplTest {

    private IssuerMetadataServiceImpl issuerMetadataService;

    @BeforeEach
    void setUp() {
        issuerMetadataService = new IssuerMetadataServiceImpl();
    }

    @Test
    void testGetIssuerMetadata() {
        IssuerMetadata metadata = issuerMetadataService.getIssuerMetadata();

        // Check main issuer fields
        assertEquals("https://issuer-url.com", metadata.credentialIssuer(), "Credential issuer domain should be 'issuer-url'");
        assertEquals("https://issuer-url.com/oidc4vci/credentials", metadata.credentialEndpoint(), "Credential endpoint should be 'issuer-url/credential'");
        assertEquals("https://issuer-url.com/oidc4vci/credentials/deferred-credential", metadata.deferredCredentialEndpoint(), "Deferred credential endpoint should be 'issuer-url/deferred-credential'");

        // Check credential configurations
        Map<String, IssuerMetadata.CredentialConfiguration> configurations = metadata.credentialConfigurationsSupported();
        assertNotNull(configurations, "Credential configurations should not be null");
        assertEquals(3, configurations.size(), "There should be 3 credential configurations supported");

        // Check LEAR_CREDENTIAL_EMPLOYEE configuration
        IssuerMetadata.CredentialConfiguration learEmployeeConfig = configurations.get(SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue());
        assertNotNull(learEmployeeConfig, "LEAR_CREDENTIAL_EMPLOYEE configuration should not be null");
        assertEquals(CredentialFormat.JWT_VC_JSON.getValue(), learEmployeeConfig.format(), "Format should be JWT_VC_JSON");
        assertEquals(List.of(DidMethods.DID_KEY.getName()), learEmployeeConfig.cryptographicBindingMethodsSupported(), "Cryptographic binding methods should be DID_KEY");
        assertEquals(List.of(SupportedSingingAlgorithms.ES256.getValue()), learEmployeeConfig.credentialSigningAlgValuesSupported(), "Credential signing algorithms should be ES256");
        assertEquals(List.of(SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue(), SupportedCredentialTypes.VERIFIABLE_CREDENTIAL.getValue()), learEmployeeConfig.credentialDefinition().type(), "Credential types should be LEAR_CREDENTIAL_EMPLOYEE and VERIFIABLE_CREDENTIAL");

        // Check LEAR_CREDENTIAL_MACHINE configuration
        IssuerMetadata.CredentialConfiguration learMachineConfig = configurations.get(SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue());
        assertNotNull(learMachineConfig, "LEAR_CREDENTIAL_MACHINE configuration should not be null");
        assertEquals(CredentialFormat.JWT_VC_JSON.getValue(), learMachineConfig.format(), "Format should be JWT_VC_JSON");
        assertNotNull(learMachineConfig.credentialDefinition(), "Credential definition should not be null");
        assertEquals(List.of(SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue(), SupportedCredentialTypes.VERIFIABLE_CREDENTIAL.getValue()), learMachineConfig.credentialDefinition().type(), "Credential types should be LEAR_CREDENTIAL_MACHINE and VERIFIABLE_CREDENTIAL");

        // Check VERIFIABLE_CERTIFICATION configuration
        IssuerMetadata.CredentialConfiguration verifiableCertificationConfig = configurations.get(SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue());
        assertNotNull(verifiableCertificationConfig, "VERIFIABLE_CERTIFICATION configuration should not be null");
        assertEquals(CredentialFormat.JWT_VC_JSON.getValue(), verifiableCertificationConfig.format(), "Format should be JWT_VC_JSON");
        assertNotNull(verifiableCertificationConfig.credentialDefinition(), "Credential definition should not be null");
        assertEquals(List.of(SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue(), SupportedCredentialTypes.VERIFIABLE_CREDENTIAL.getValue()), verifiableCertificationConfig.credentialDefinition().type(), "Credential types should be VERIFIABLE_CERTIFICATION and VERIFIABLE_CREDENTIAL");
    }

    @Test
    void testHasCryptographicBindingWithDidKey() {
        // Case 1: Credential type with cryptographic binding (should return true)
        assertTrue(issuerMetadataService.hasCryptographicBindingWithDidKey(SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue()),
                "Expected LEAR_CREDENTIAL_EMPLOYEE to have cryptographic binding with did:key");

        // Case 2: Credential type without cryptographic binding (should return false)
        assertFalse(issuerMetadataService.hasCryptographicBindingWithDidKey(SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue()),
                "Expected LEAR_CREDENTIAL_MACHINE to not have cryptographic binding with did:key");

        // Case 3: Unsupported credential type (should throw IllegalArgumentException)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        issuerMetadataService.hasCryptographicBindingWithDidKey("UNSUPPORTED_CREDENTIAL_TYPE"),
                "Expected an IllegalArgumentException for unsupported credential type");

        assertEquals("Credential type 'UNSUPPORTED_CREDENTIAL_TYPE' is not supported.", exception.getMessage());
    }
}