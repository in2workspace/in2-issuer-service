package es.in2.issuer.backoffice.model.validation.impl;

import es.in2.issuer.backoffice.model.dto.CredentialRequest;
import es.in2.issuer.backoffice.model.enums.OperationMode;
import es.in2.issuer.backoffice.model.validation.ValidityPeriodConditional;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidityPeriodValidator implements ConstraintValidator<ValidityPeriodConditional, CredentialRequest> {

    @Override
    public boolean isValid(CredentialRequest request, ConstraintValidatorContext context) {
        if (request.operation_mode() == OperationMode.A) {
            return request.validityPeriod() != null;
        }
        return true;
    }

}
