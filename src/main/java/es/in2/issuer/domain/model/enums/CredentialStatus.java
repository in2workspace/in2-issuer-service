package es.in2.issuer.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CredentialStatus {

    WITHDRAWN("withdrawn"),
    PEND_DOWNLOAD("pend_download"),
    ISSUED("issued"),
    VALID("valid"),
    REVOKED("revoked"),
    EXPIRED("expired");

    private final String value;

}
