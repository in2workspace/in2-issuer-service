package es.in2.issuer.oidc4vci.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SupportedSingingAlgorithms {

    ES256("ES256");

    private final String value;
}