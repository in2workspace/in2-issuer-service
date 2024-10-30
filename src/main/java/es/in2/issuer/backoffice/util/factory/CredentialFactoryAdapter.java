package es.in2.issuer.backoffice.util.factory;

import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.CredentialRequest;
import es.in2.issuer.backoffice.model.enums.SupportedCredentialTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CredentialFactoryAdapter {

    private final LEARCredentialEmployeeFactory LEARCredentialEmployeeFactory;

    public Credential createDecodedCredential (CredentialRequest credentialRequest){
        // if credentialRequest.schema() is LEARCredentialEmployee
            // return LEARCredentialEmployeeFactory.createDecodedCredential(credentialRequest.payload())
        // if credentialRequest.schema() is VerifiableCertification
            // return VerifiableCertificationFactory.createDecodedCredential(credentialRequest.payload())
        if(credentialRequest.schema().equals(SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue()))

            switch (credentialRequest.schema()){

            }


        return null;
    }

}
