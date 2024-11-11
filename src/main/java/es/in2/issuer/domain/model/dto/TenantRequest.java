package es.in2.issuer.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TenantRequest(
        @JsonProperty("tenant_name") @NotNull @NotBlank String tenantName,
        @JsonProperty("tenant_domain") @NotNull @NotBlank String tenantDomain
) {
}
