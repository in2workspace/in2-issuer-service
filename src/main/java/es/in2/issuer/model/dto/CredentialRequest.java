package es.in2.issuer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.in2.issuer.model.enums.CredentialFormat;
import es.in2.issuer.model.enums.OperationMode;
import es.in2.issuer.model.validation.ValidityPeriodConditional;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidityPeriodConditional
public record CredentialRequest(
        @JsonProperty("schema") @NotNull @NotBlank String schema,
        @JsonProperty("format") @NotNull @NotBlank CredentialFormat format,
        @JsonProperty("payload") @NotNull Payload payload,
        @JsonProperty("operation_mode") @NotNull @NotBlank OperationMode operation_mode,
        @JsonProperty("validity_period") Integer validityPeriod,
        @JsonProperty("response_uri") String responseUri
) {
}
