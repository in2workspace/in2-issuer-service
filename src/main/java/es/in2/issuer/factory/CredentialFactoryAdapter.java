package es.in2.issuer.factory;

import es.in2.issuer.model.dto.CredentialRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CredentialFactoryAdapter {

    private final LEARCredentialEmployeeFactory LEARCredentialEmployeeFactory;

    public String createDecodedCredential (CredentialRequest credentialRequest){
        // if credentialRequest.schema() is LEARCredentialEmployee
            // return LEARCredentialEmployeeFactory.createDecodedCredential(credentialRequest.payload())
        // if credentialRequest.schema() is VerifiableCertification
            // return VerifiableCertificationFactory.createDecodedCredential(credentialRequest.payload())


        return null;
    }

}
