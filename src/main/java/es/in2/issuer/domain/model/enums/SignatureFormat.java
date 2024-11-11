package es.in2.issuer.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignatureFormat {

    JADES("jades", "JAdES Signature"),
    CADES("cades", "CAdES Signature"),
    PADES("pades", "PAdES Signature");

    private final String value;
    private final String description;

}
