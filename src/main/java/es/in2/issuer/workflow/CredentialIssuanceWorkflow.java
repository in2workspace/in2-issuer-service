package es.in2.issuer.workflow;

import es.in2.issuer.model.dto.CredentialRequest;

public interface CredentialIssuanceWorkflow {

    void issueCredential(String authorizationHeader, CredentialRequest credentialRequest);

}
