package es.in2.issuer.backoffice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
public record LEARCredentialMachine(
        @JsonProperty("@context") List<String> context,
        @JsonProperty("id") String id,
        @JsonProperty("type") List<String> type,
        @JsonProperty("credentialSubject") CredentialSubject credentialSubject,
        @JsonProperty("expirationDate") String expirationDate,
        @JsonProperty("issuanceDate") String issuanceDate,
        @JsonProperty("issuer") String issuer,
        @JsonProperty("validFrom") String validFrom
) implements VerifiableCredential {
    @Builder
    public record CredentialSubject(
            @JsonProperty("mandate") Mandate mandate
    ) {

        @Builder
        public record Mandate(
                @JsonProperty("id") String id,
                @JsonProperty("life_span") LifeSpan lifeSpan,
                @JsonProperty("mandatee") Mandatee mandatee,
                @JsonProperty("mandator") Mandator mandator,
                @JsonProperty("power") List<Power> power,
                @JsonProperty("signer") Signer signer
        ) {
            @Builder
            public record LifeSpan(
                    @JsonProperty("end_date_time") String endDateTime,
                    @JsonProperty("start_date_time") String startDateTime
            ) {
            }

            @Builder
            public record Mandatee(
                    @JsonProperty("id") String id,
                    @JsonProperty("serviceName") String serviceName,
                    @JsonProperty("serviceType") String serviceType,
                    @JsonProperty("version") String version,
                    @JsonProperty("domain") String domain,
                    @JsonProperty("ipAddress") String ipAddress,
                    @JsonProperty("description") String description,
                    @JsonProperty("contact") Contact contact
            ) {
                @Builder
                public record Contact(
                        @JsonProperty("email") String email,
                        @JsonProperty("phone") String phone
                ) {
                }
            }

            @Builder
            public record Mandator(
                    @JsonProperty("commonName") String commonName,
                    @JsonProperty("country") String country,
                    @JsonProperty("emailAddress") String emailAddress,
                    @JsonProperty("organization") String organization,
                    @JsonProperty("organizationIdentifier") String organizationIdentifier,
                    @JsonProperty("serialNumber") String serialNumber
            ) {
            }

            @Builder
            public record Power(
                    @JsonProperty("id") String id,
                    @JsonProperty("tmf_action") Object tmfAction,
                    @JsonProperty("tmf_domain") String tmfDomain,
                    @JsonProperty("tmf_function") String tmfFunction,
                    @JsonProperty("tmf_type") String tmfType
            ) {
            }

            @Builder
            public record Signer(
                    @JsonProperty("commonName") String commonName,
                    @JsonProperty("country") String country,
                    @JsonProperty("emailAddress") String emailAddress,
                    @JsonProperty("organization") String organization,
                    @JsonProperty("organizationIdentifier") String organizationIdentifier,
                    @JsonProperty("serialNumber") String serialNumber
            ) {
            }

        }
    }
}
