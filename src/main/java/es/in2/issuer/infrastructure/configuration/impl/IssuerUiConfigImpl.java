package es.in2.issuer.infrastructure.configuration.impl;

import es.in2.issuer.infrastructure.configuration.IssuerUiConfig;
import es.in2.issuer.infrastructure.configuration.properties.IssuerUiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IssuerUiConfigImpl implements IssuerUiConfig {

    private final IssuerUiProperties issuerUiProperties;

    @Override
    public String getIssuerUiUrl() {
        return issuerUiProperties.url();
    }

}
