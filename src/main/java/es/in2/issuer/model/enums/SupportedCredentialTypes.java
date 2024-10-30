package es.in2.issuer.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SupportedCredentialTypes {

    LEAR_CREDENTIAL_EMPLOYEE("LEARCredentialEmployee"),
    LEAR_CREDENTIAL_MACHINE("LEARCredentialMachine"),
    VERIFIABLE_CERTIFICATION("VerifiableCertification");

    private final String value;

}
