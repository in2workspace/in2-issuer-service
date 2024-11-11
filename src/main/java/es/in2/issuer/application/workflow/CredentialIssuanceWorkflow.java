package es.in2.issuer.application.workflow;

import es.in2.issuer.domain.model.dto.CredentialRequest;

public interface CredentialIssuanceWorkflow {

    void issueCredential(String authorizationHeader, CredentialRequest credentialRequest);

}
