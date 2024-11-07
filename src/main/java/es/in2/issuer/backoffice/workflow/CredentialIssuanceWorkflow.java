package es.in2.issuer.backoffice.workflow;

import es.in2.issuer.backoffice.model.dto.CredentialRequest;

public interface CredentialIssuanceWorkflow {

    void issueCredential(String authorizationHeader, CredentialRequest credentialRequest);

}
