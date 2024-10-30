package es.in2.issuer.workflow.impl;

import es.in2.issuer.model.dto.CredentialRequest;
import es.in2.issuer.security.service.PolicyAuthorizationService;
import es.in2.issuer.workflow.CredentialIssuanceWorkflow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialIssuanceWorkflowImpl implements CredentialIssuanceWorkflow {

    private final PolicyAuthorizationService policyAuthorizationService;

    @Override
    public void issueCredential(String authorizationHeader, CredentialRequest credentialRequest) {

        // 0. Check User Policies
        policyAuthorizationService.authorize(authorizationHeader, credentialRequest.schema());

        /*
            En este punto del código, los casos de uso con los que trabajamos son:
                - El usuario tiene una VC de tipo "LEARCredentialMachine" y puede emitir VC de tipo "VerifiableCertification".
                - El usuario tiene una VC de tipo "LEARCredentialEmployee" y puede emitir VC de tipo "LEARCredentialEmployee" o "LEARCredentialMachine".
         */

        // 1. Build a Credential with CredentialRequest
            // Create unsigned credential
                // return a Credential object with the data from the CredentialRequest

        // 2. Sign Credential if necessary
            // if OperationMode is A || credential has cryptographic_binding
                // --> deferred signature
            // else
                // sign Credential and return VerifiableCredential object

        // 3. Create a Procedure (the procedure is used to track the progress of the credential issuance).
            // ProcedureType: CREDENTIAL_ISSUANCE (constant)
            // ProcedureStatus:
            // ProcedureDataDecoded: JSON object (Credential)
            // ProcedureDataEncoded: JWT (Verifiable Credential)

    }

}
