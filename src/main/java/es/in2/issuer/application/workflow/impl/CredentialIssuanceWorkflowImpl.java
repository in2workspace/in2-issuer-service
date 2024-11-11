package es.in2.issuer.application.workflow.impl;

import es.in2.issuer.domain.model.dto.CredentialRequest;
import es.in2.issuer.application.workflow.CredentialIssuanceWorkflow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialIssuanceWorkflowImpl implements CredentialIssuanceWorkflow {

    @Override
    public void issueCredential(String authorizationHeader, CredentialRequest credentialRequest) {
        // Create a Procedure. The procedure is used to track the progress of the credential issuance.

    }

}
