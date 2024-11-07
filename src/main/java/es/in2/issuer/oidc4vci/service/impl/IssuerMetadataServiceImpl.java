package es.in2.issuer.oidc4vci.service.impl;

import es.in2.issuer.backoffice.model.enums.CredentialFormat;
import es.in2.issuer.backoffice.model.enums.DidMethods;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import es.in2.issuer.oidc4vci.model.enums.SupportedSingingAlgorithms;
import es.in2.issuer.oidc4vci.model.dto.IssuerMetadata;
import es.in2.issuer.oidc4vci.service.IssuerMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssuerMetadataServiceImpl implements IssuerMetadataService {
    @Override
    public IssuerMetadata getIssuerMetadata() {
        String credentialIssuerDomain = "https://issuer-url.com"; // todo: Get issuer URL from configs

        IssuerMetadata.CredentialConfiguration learCredentialEmployee = IssuerMetadata.CredentialConfiguration.builder()
                .format(CredentialFormat.JWT_VC_JSON.getValue())
                .cryptographicBindingMethodsSupported(List.of(DidMethods.DID_KEY.getName()))
                .credentialSigningAlgValuesSupported(List.of(SupportedSingingAlgorithms.ES256.getValue()))
                .credentialDefinition(IssuerMetadata.CredentialConfiguration.CredentialDefinition.builder()
                        .type(List.of(
                                SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue(),
                                SupportedCredentialTypes.VERIFIABLE_CREDENTIAL.getValue())).build())
                .build();

        IssuerMetadata.CredentialConfiguration learCredentialMachine = IssuerMetadata.CredentialConfiguration.builder()
                .format(CredentialFormat.JWT_VC_JSON.getValue())
                .credentialDefinition(IssuerMetadata.CredentialConfiguration.CredentialDefinition.builder()
                        .type(List.of(
                                SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue(),
                                SupportedCredentialTypes.VERIFIABLE_CREDENTIAL.getValue())).build())
                .build();

        IssuerMetadata.CredentialConfiguration verifiableCertification = IssuerMetadata.CredentialConfiguration.builder()
                .format(CredentialFormat.JWT_VC_JSON.getValue())
                .credentialDefinition(IssuerMetadata.CredentialConfiguration.CredentialDefinition.builder()
                        .type(List.of(
                                SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue(),
                                SupportedCredentialTypes.VERIFIABLE_CREDENTIAL.getValue())).build())
                .build();

        Map<String, IssuerMetadata.CredentialConfiguration> credentialConfigurations = Map.of(
                SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue(), learCredentialEmployee,
                SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue(), learCredentialMachine,
                SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue(), verifiableCertification
        );

        return IssuerMetadata.builder()
                .credentialIssuer(credentialIssuerDomain)
                .credentialEndpoint(credentialIssuerDomain + "/oidc4vci/credentials")
                .deferredCredentialEndpoint(credentialIssuerDomain + "/oidc4vci/credentials/deferred-credential")
                .credentialConfigurationsSupported(credentialConfigurations)
                .build();
    }
}
