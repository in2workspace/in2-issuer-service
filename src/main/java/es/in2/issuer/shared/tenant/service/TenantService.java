package es.in2.issuer.shared.tenant.service;

import es.in2.issuer.shared.tenant.model.TenantDetails;
import es.in2.issuer.shared.tenant.model.TenantRequest;
import es.in2.issuer.shared.tenant.model.TenantResponse;

public interface TenantService {

    void createTenant(String authorizationHeader, TenantRequest tenantRequest);

    void updateTenant(String authorizationHeader, String tenantId, TenantRequest tenantRequest);

    TenantResponse getTenants(String authorizationHeader);

    TenantDetails getTenant(String authorizationHeader, String tenantId);

}
