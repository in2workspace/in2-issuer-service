package es.in2.issuer.domain.service;

import es.in2.issuer.domain.model.dto.TenantDetails;
import es.in2.issuer.domain.model.dto.TenantRequest;
import es.in2.issuer.domain.model.dto.TenantResponse;

public interface TenantService {

    void createTenant(String authorizationHeader, TenantRequest tenantRequest);

    void updateTenant(String authorizationHeader, String tenantId, TenantRequest tenantRequest);

    TenantResponse getTenants(String authorizationHeader);

    TenantDetails getTenant(String authorizationHeader, String tenantId);

}
