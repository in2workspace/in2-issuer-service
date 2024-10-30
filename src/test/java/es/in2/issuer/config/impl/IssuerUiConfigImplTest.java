package es.in2.issuer.config.impl;

import es.in2.issuer.shared.config.impl.IssuerUiConfigImpl;
import es.in2.issuer.shared.config.properties.IssuerUiProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IssuerUiConfigImplTest {

    @ParameterizedTest
    @CsvSource({
            "https://valid-url.com, https://valid-url.com",
            "'', ''",
            "htp://invalid-url, htp://invalid-url",
            "null, null"  // Usaremos el texto 'null' como placeholder para representar valores nulos.
    })
    void testIssuerUiUrlWithVariousInputs(String url, String expected) {
        IssuerUiProperties properties = new IssuerUiProperties("null".equals(url) ? null : url);
        IssuerUiConfigImpl config = new IssuerUiConfigImpl(properties);
        if ("null".equals(expected)) {
            assertNull(config.getIssuerUiUrl());
        } else {
            assertEquals(expected, config.getIssuerUiUrl());
        }
    }

}
