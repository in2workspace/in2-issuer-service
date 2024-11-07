package es.in2.issuer.shared.config.impl;

import es.in2.issuer.shared.config.IssuerUiConfig;
import es.in2.issuer.shared.config.properties.IssuerUiProperties;
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
