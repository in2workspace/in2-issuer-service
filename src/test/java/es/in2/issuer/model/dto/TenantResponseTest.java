package es.in2.issuer.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.TenantDetails;
import es.in2.issuer.backoffice.model.dto.TenantResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TenantResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSerialization() throws Exception {
        TenantDetails tenant1 = TenantDetails.builder()
                .tenantId("123")
                .tenantName("Tenant One")
                .tenantDomain("tenant1.com")
                .build();

        TenantDetails tenant2 = TenantDetails.builder()
                .tenantId("456")
                .tenantName("Tenant Two")
                .tenantDomain("tenant2.com")
                .build();

        TenantResponse tenantResponse = TenantResponse.builder()
                .tenantDetailsList(List.of(tenant1, tenant2))
                .build();

        String json = objectMapper.writeValueAsString(tenantResponse);

        String expectedJson = """
                {
                    "tenants": [
                        {
                            "tenant_id": "123",
                            "tenant_name": "Tenant One",
                            "tenant_domain": "tenant1.com"
                        },
                        {
                            "tenant_id": "456",
                            "tenant_name": "Tenant Two",
                            "tenant_domain": "tenant2.com"
                        }
                    ]
                }
                """;

        assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(json),
                "Serialized JSON does not match expected structure");
    }

    @Test
    void testDeserialization() throws Exception {
        String json = """
                {
                    "tenants": [
                        {
                            "tenant_id": "123",
                            "tenant_name": "Tenant One",
                            "tenant_domain": "tenant1.com"
                        },
                        {
                            "tenant_id": "456",
                            "tenant_name": "Tenant Two",
                            "tenant_domain": "tenant2.com"
                        }
                    ]
                }
                """;

        TenantResponse tenantResponse = objectMapper.readValue(json, TenantResponse.class);

        List<TenantDetails> tenants = tenantResponse.tenantDetailsList();
        assertEquals(2, tenants.size(), "Expected two tenants in the deserialized list");

        TenantDetails tenant1 = tenants.get(0);
        assertEquals("123", tenant1.tenantId(), "Tenant 1 ID does not match");
        assertEquals("Tenant One", tenant1.tenantName(), "Tenant 1 name does not match");
        assertEquals("tenant1.com", tenant1.tenantDomain(), "Tenant 1 domain does not match");

        TenantDetails tenant2 = tenants.get(1);
        assertEquals("456", tenant2.tenantId(), "Tenant 2 ID does not match");
        assertEquals("Tenant Two", tenant2.tenantName(), "Tenant 2 name does not match");
        assertEquals("tenant2.com", tenant2.tenantDomain(), "Tenant 2 domain does not match");
    }

}
