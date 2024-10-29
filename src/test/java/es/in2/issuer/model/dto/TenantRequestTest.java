package es.in2.issuer.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TenantRequestTest {

    private static Validator validator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidTenantRequest() {
        TenantRequest tenantRequest = new TenantRequest("Test Tenant", "test.com");

        Set<ConstraintViolation<TenantRequest>> violations = validator.validate(tenantRequest);
        assertTrue(violations.isEmpty(), "Expected no validation errors for valid TenantRequest");
    }

    @Test
    void testInvalidTenantRequest_NullName() {
        TenantRequest tenantRequest = new TenantRequest(null, "test.com");

        Set<ConstraintViolation<TenantRequest>> violations = validator.validate(tenantRequest);
        assertEquals(2, violations.size(), "Expected one validation error for null or blank tenantName");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tenantName") &&
                v.getMessage().contains("must not be null")), "Expected tenantName to be not null");
    }

    @Test
    void testInvalidTenantRequest_BlankName() {
        TenantRequest tenantRequest = new TenantRequest("  ", "test.com");

        Set<ConstraintViolation<TenantRequest>> violations = validator.validate(tenantRequest);
        assertEquals(1, violations.size(), "Expected one validation error for blank tenantName");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tenantName") &&
                v.getMessage().contains("must not be blank")), "Expected tenantName to be not blank");
    }

    @Test
    void testInvalidTenantRequest_NullDomain() {
        TenantRequest tenantRequest = new TenantRequest("Test Tenant", null);

        Set<ConstraintViolation<TenantRequest>> violations = validator.validate(tenantRequest);
        assertEquals(2, violations.size(), "Expected one validation error for null or blank tenantDomain");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tenantDomain") &&
                v.getMessage().contains("must not be null")), "Expected tenantDomain to be not null");
    }

    @Test
    void testInvalidTenantRequest_BlankDomain() {
        TenantRequest tenantRequest = new TenantRequest("Test Tenant", "  ");

        Set<ConstraintViolation<TenantRequest>> violations = validator.validate(tenantRequest);
        assertEquals(1, violations.size(), "Expected one validation error for blank tenantDomain");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tenantDomain") &&
                v.getMessage().contains("must not be blank")), "Expected tenantDomain to be not blank");
    }

    @Test
    void testSerialization() throws Exception {
        TenantRequest tenantRequest = new TenantRequest("Test Tenant", "test.com");

        String json = objectMapper.writeValueAsString(tenantRequest);
        String expectedJson = """
                {
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
                    "tenant_name": "Test Tenant",
                    "tenant_domain": "test.com"
                }
                """;

        TenantRequest tenantRequest = objectMapper.readValue(json, TenantRequest.class);

        assertEquals("Test Tenant", tenantRequest.tenantName(), "Tenant name does not match");
        assertEquals("test.com", tenantRequest.tenantDomain(), "Tenant domain does not match");
    }

}
