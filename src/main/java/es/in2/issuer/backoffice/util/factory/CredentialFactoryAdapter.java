package es.in2.issuer.backoffice.util.factory;

import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.CredentialRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CredentialFactoryAdapter {

    private final LEARCredentialEmployeeFactory learCredentialEmployeeFactory;
    private final LEARCredentialMachineFactory learCredentialMachineFactory;
    private final VerifiableCertificationFactory verifiableCertificationFactory;

    public Credential createCredential(CredentialRequest credentialRequest) {
        return switch (credentialRequest.schema()) {
            case "LEARCredentialEmployee" ->
                    learCredentialEmployeeFactory.createCredential(credentialRequest.payload());
            case "LEARCredentialMachine" ->
                    learCredentialMachineFactory.createCredential(credentialRequest.payload());
            case "VerifiableCertification" ->
                    verifiableCertificationFactory.createCredential(credentialRequest.payload());
            default ->
                    throw new IllegalArgumentException("Credential Schema: " + credentialRequest.schema() + " is not supported");
        };
    }

}
