package es.in2.issuer.backoffice.workflow.impl;

import es.in2.issuer.backoffice.model.dto.Credential;
import es.in2.issuer.backoffice.model.dto.CredentialRequest;
import es.in2.issuer.backoffice.model.enums.OperationMode;
import es.in2.issuer.backoffice.util.factory.CredentialFactoryAdapter;
import es.in2.issuer.oidc4vci.service.IssuerMetadataService;
import es.in2.issuer.shared.security.service.PolicyAuthorizationService;
import es.in2.issuer.backoffice.workflow.CredentialIssuanceWorkflow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialIssuanceWorkflowImpl implements CredentialIssuanceWorkflow {

    private final PolicyAuthorizationService policyAuthorizationService;
    private final CredentialFactoryAdapter credentialFactoryAdapter;
    private final IssuerMetadataService issuerMetadataService;

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
        Credential credential = credentialFactoryAdapter.createCredential(credentialRequest);

        // 2. Sign Credential if necessary
            // if OperationMode is A || credential has cryptographic_binding
                // --> deferred signature
            // else
                // sign Credential and return VerifiableCredential object

        String ProcedureDataEncoded = null;
        if(credentialRequest.operation_mode().equals(OperationMode.S) ||
                !issuerMetadataService.hasCryptographicBindingWithDidKey(credentialRequest.schema())){
            // Sign credential using DSS
            ProcedureDataEncoded = "signed credential";
        }

        // 3. Create a Procedure (the procedure is used to track the progress of the credential issuance).
            // ProcedureType: CREDENTIAL_ISSUANCE (constant)
            // ProcedureStatus:
            // ProcedureDataDecoded: JSON object (Credential)
            // ProcedureDataEncoded: JWT (Verifiable Credential)

        // 4. Generate transaction_code and send credential offer email
            // Generate random transaction_code
            // Save transaction_code and procedure_id in cache
            // Send credential offer email with link to UI (transaction_code)
    }

}
