package es.in2.issuer.infrastructure.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "issuer-ui")
public record IssuerUiProperties(
        @NotNull @NotBlank @URL String url
) {
}
