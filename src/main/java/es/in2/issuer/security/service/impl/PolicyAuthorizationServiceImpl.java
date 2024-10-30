package es.in2.issuer.security.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import es.in2.issuer.model.enums.SupportedCredentialTypes;
import es.in2.issuer.security.service.JwtService;
import es.in2.issuer.security.service.PolicyAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import static es.in2.issuer.exception.ErrorConstantsMessage.AUTHORIZATION_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
public class PolicyAuthorizationServiceImpl implements PolicyAuthorizationService {

    private final JwtService jwtService;

    @Override
    public void authorize(String authorizationHeader, String schema) {
        JsonNode parsedJwt = jwtService.parseJwt(authorizationHeader);

        // Policy 1: if authorizationHeader has a VC of type "LEARCredentialMachine",
        // it could not issue credentials of type "LEARCredentialEmployee"
        // and "LEARCredentialMachine".
        authorizeLearCredentialMachine(parsedJwt, schema);

        // Policy 2: if authorizationHeader has a VC of type "LEARCredentialEmployee",
        // it could not issue credentials of type "VerifiableCertification".
        authorizeLearCredentialEmployee(parsedJwt, schema);

        // Policy 3: if authorizationHeader has a VC of type "LEARCredentialEmployee" and role is not "admin",
        // it could issue a "LEARCredentialEmployee" without "Onboarding" power.
        // todo: think about how to implement this policy

    }

    private void authorizeLearCredentialEmployee(JsonNode parsedJwt, String schema) {
        String learCredentialEmployeeTypeName = SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue();
        String verifiableCertificationTypeName = SupportedCredentialTypes.VERIFIABLE_CERTIFICATION.getValue();
        if (parsedJwt.toString().contains(learCredentialEmployeeTypeName) && schema.equals(verifiableCertificationTypeName)) {
            throw new AuthorizationServiceException(AUTHORIZATION_ERROR_MESSAGE);
        }
    }

    private void authorizeLearCredentialMachine(JsonNode parsedJwt, String schema) {
        String learCredentialMachineTypeName = SupportedCredentialTypes.LEAR_CREDENTIAL_MACHINE.getValue();
        String learCredentialEmployeeTypeName = SupportedCredentialTypes.LEAR_CREDENTIAL_EMPLOYEE.getValue();
        if ((parsedJwt.toString().contains(learCredentialMachineTypeName) && schema.equals(learCredentialEmployeeTypeName))
                || (parsedJwt.toString().contains(learCredentialMachineTypeName) && schema.equals(learCredentialMachineTypeName))) {
            throw new AuthorizationServiceException(AUTHORIZATION_ERROR_MESSAGE);
        }
    }

}
