package es.in2.issuer.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.issuer.backoffice.model.dto.TenantDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TenantDetailsTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSerialization() throws Exception {
        TenantDetails tenantDetails = TenantDetails.builder()
                .tenantId("123")
                .tenantName("Test Tenant")
                .tenantDomain("test.com")
                .build();

        String json = objectMapper.writeValueAsString(tenantDetails);

        String expectedJson = """
                {
                    "tenant_id": "123",
                    "tenant_name": "Test Tenant",
                    "tenant_domain": "test.com"
                }
                """;

        assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(json),
                "Serialized JSON does not match expected structure");
    }

    @Test
    void testDeserialization() throws Exception {
        String json = """
                {
                    "tenant_id": "123",
                    "tenant_name": "Test Tenant",
                    "tenant_domain": "test.com"
                }
                """;

        TenantDetails tenantDetails = objectMapper.readValue(json, TenantDetails.class);

        assertEquals("123", tenantDetails.tenantId(), "Tenant ID does not match");
        assertEquals("Test Tenant", tenantDetails.tenantName(), "Tenant name does not match");
        assertEquals("test.com", tenantDetails.tenantDomain(), "Tenant domain does not match");
    }

}
