package es.in2.issuer.backoffice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CredentialFormat {

    JWT_VC_JSON("jwt_vc_json" );

    private final String value;

}
