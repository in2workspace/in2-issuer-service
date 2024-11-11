package es.in2.issuer.model.entity;

import es.in2.issuer.domain.model.entity.Tenant;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TenantTest {

    @Test
    void testNoArgsConstructor() {
        Tenant tenant = new Tenant();

        assertNull(tenant.getTenantId(), "Expected tenantId to be null");
        assertNull(tenant.getTenantName(), "Expected tenantName to be null");
        assertNull(tenant.getTenantDomain(), "Expected tenantDomain to be null");
    }

    @Test
    void testAllArgsConstructor() {
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant(tenantId, "Test Tenant", "test.com");

        assertEquals(tenantId, tenant.getTenantId(), "Tenant ID does not match");
        assertEquals("Test Tenant", tenant.getTenantName(), "Tenant name does not match");
        assertEquals("test.com", tenant.getTenantDomain(), "Tenant domain does not match");
    }

    @Test
    void testBuilder() {
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = Tenant.builder()
                .tenantId(tenantId)
                .tenantName("Test Tenant")
                .tenantDomain("test.com")
                .build();

        assertEquals(tenantId, tenant.getTenantId(), "Tenant ID does not match");
        assertEquals("Test Tenant", tenant.getTenantName(), "Tenant name does not match");
        assertEquals("test.com", tenant.getTenantDomain(), "Tenant domain does not match");
    }

    @Test
    void testSettersAndGetters() {
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant();
        tenant.setTenantId(tenantId);
        tenant.setTenantName("Updated Tenant");
        tenant.setTenantDomain("updated.com");

        assertEquals(tenantId, tenant.getTenantId(), "Tenant ID does not match after setting");
        assertEquals("Updated Tenant", tenant.getTenantName(), "Tenant name does not match after setting");
        assertEquals("updated.com", tenant.getTenantDomain(), "Tenant domain does not match after setting");
    }

}
