package es.in2.issuer.config.properties;

import es.in2.issuer.infrastructure.configuration.properties.IssuerUiProperties;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IssuerUiPropertiesTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenUrlIsValid_thenNoValidationErrors() {
        IssuerUiProperties validUrlProperties = new IssuerUiProperties("https://valid-url.com");
        Set<ConstraintViolation<IssuerUiProperties>> violations = validator.validate(validUrlProperties);
        assertTrue(violations.isEmpty(), "Expected no validation errors for valid URL");
    }

    @Test
    void whenUrlIsNull_thenValidationErrors() {
        IssuerUiProperties nullUrlProperties = new IssuerUiProperties(null);
        Set<ConstraintViolation<IssuerUiProperties>> violations = validator.validate(nullUrlProperties);
        assertFalse(violations.isEmpty(), "Expected validation errors for null URL");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("url") &&
                v.getMessage().contains("must not be null")), "Expected URL not null validation error");
    }

    @Test
    void whenUrlIsBlank_thenValidationErrors() {
        IssuerUiProperties blankUrlProperties = new IssuerUiProperties("");
        Set<ConstraintViolation<IssuerUiProperties>> violations = validator.validate(blankUrlProperties);
        assertFalse(violations.isEmpty(), "Expected validation errors for blank URL");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("url") &&
                v.getMessage().contains("must not be blank")), "Expected URL not blank validation error");
    }

    @Test
    void whenUrlIsMalformed_thenValidationErrors() {
        IssuerUiProperties malformedUrlProperties = new IssuerUiProperties("htp://invalid-url");
        Set<ConstraintViolation<IssuerUiProperties>> violations = validator.validate(malformedUrlProperties);
        assertFalse(violations.isEmpty(), "Expected validation errors for malformed URL");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("url") &&
                v.getMessage().contains("must be a valid URL")), "Expected URL format validation error");
    }

}
