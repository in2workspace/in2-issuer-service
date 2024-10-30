package es.in2.issuer.backoffice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationMode {

    S("S", "Synchronous"),
    A("A", "Asynchronous");

    private final String value;
    private final String description;

}
