package es.in2.issuer.backoffice.model.validation;

import es.in2.issuer.backoffice.model.validation.impl.ValidityPeriodValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidityPeriodValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidityPeriodConditional {
    String message() default "Validity period is required when operation mode is OperationMode.A";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
