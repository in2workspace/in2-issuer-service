package es.in2.issuer.config;

import es.in2.issuer.shared.config.IssuerUiConfig;
import es.in2.issuer.shared.config.impl.IssuerUiConfigImpl;
import es.in2.issuer.shared.config.properties.IssuerUiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IssuerUiConfigTest {

    private IssuerUiConfig issuerUiConfig;

    @BeforeEach
    void setUp() {
        // Puedes ajustar esto para usar una implementación concreta si existe
        IssuerUiProperties properties = Mockito.mock(IssuerUiProperties.class);
        Mockito.when(properties.url()).thenReturn("https://valid-url.com");
        issuerUiConfig = new IssuerUiConfigImpl(properties);
    }

    @Test
    void testGetIssuerUiUrl_withValidUrl() {
        assertEquals("https://valid-url.com", issuerUiConfig.getIssuerUiUrl());
    }

    @Test
    void testGetIssuerUiUrl_withNullUrl() {
        IssuerUiProperties properties = new IssuerUiProperties(null);
        IssuerUiConfig configWithNullUrl = new IssuerUiConfigImpl(properties);
        assertNull(configWithNullUrl.getIssuerUiUrl(), "Expected null when URL is not set in properties");
    }

    @Test
    void testGetIssuerUiUrl_withEmptyUrl() {
        IssuerUiProperties properties = new IssuerUiProperties("");
        IssuerUiConfig configWithEmptyUrl = new IssuerUiConfigImpl(properties);
        assertEquals("", configWithEmptyUrl.getIssuerUiUrl(), "Expected empty string when URL is set to blank");
    }

}
