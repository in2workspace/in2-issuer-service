package es.in2.issuer.shared.tenant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TenantDetails(
        @JsonProperty("tenant_id") String tenantId,
        @JsonProperty("tenant_name") String tenantName,
        @JsonProperty("tenant_domain") String tenantDomain
) {
}
