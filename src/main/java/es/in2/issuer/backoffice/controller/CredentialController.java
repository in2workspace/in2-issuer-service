package es.in2.issuer.backoffice.controller;

import es.in2.issuer.backoffice.model.dto.CredentialRequest;
import es.in2.issuer.backoffice.workflow.CredentialIssuanceWorkflow;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issuer/v1/credentials")
@RequiredArgsConstructor
public class CredentialController {

    private final CredentialIssuanceWorkflow credentialIssuanceWorkflow;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCredential(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                 @Valid @RequestBody CredentialRequest credentialRequest) {
        credentialIssuanceWorkflow.issueCredential(authorizationHeader, credentialRequest);
    }

}
