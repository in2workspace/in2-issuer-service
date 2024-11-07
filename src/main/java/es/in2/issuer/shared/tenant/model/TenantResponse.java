package es.in2.issuer.shared.tenant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record TenantResponse(
        @JsonProperty("tenants") List<TenantDetails> tenantDetailsList
) {
}
