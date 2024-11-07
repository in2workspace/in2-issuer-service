package es.in2.issuer.backoffice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Credential(
        @JsonProperty("sub")
        String subject,

        @JsonProperty("nbf")
        Long notValidBefore,

        @JsonProperty("iss")
        String issuer,

        @JsonProperty("exp")
        Long expirationTime,

        @JsonProperty("iat")
        Long issuedAt,

        @JsonProperty("vc")
        VerifiableCredential verifiableCredential,

        @JsonProperty("jti")
        String jwtId
) {
}

