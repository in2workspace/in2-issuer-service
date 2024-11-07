package es.in2.issuer.backoffice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DidMethods {
    
    DID_ELSI("did:elsi:"),
    DID_KEY("did:key:");

    private final String name;

}
