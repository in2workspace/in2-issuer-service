package es.in2.issuer.oidc4vci.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record IssuerMetadata(@JsonProperty("credential_issuer") String credentialIssuer,
                             @JsonProperty("credential_endpoint") String credentialEndpoint,
                             @JsonProperty("deferred_credential_endpoint") String deferredCredentialEndpoint,
                             @JsonProperty("credential_configurations_supported") Map<String, CredentialConfiguration> credentialConfigurationsSupported
) {
    @Builder
    public record CredentialConfiguration(@JsonProperty("format") String format,
                                          @JsonInclude(JsonInclude.Include.NON_NULL)
                                          @JsonProperty("cryptographic_binding_methods_supported") List<String> cryptographicBindingMethodsSupported,
                                          @JsonInclude(JsonInclude.Include.NON_NULL)
                                          @JsonProperty("credential_signing_alg_values_supported") List<String> credentialSigningAlgValuesSupported,
                                          @JsonProperty("credential_definition") CredentialDefinition credentialDefinition) {
        @Builder
        public record CredentialDefinition(
                @JsonProperty("type") List<String> type
        ) {
        }
    }
}
