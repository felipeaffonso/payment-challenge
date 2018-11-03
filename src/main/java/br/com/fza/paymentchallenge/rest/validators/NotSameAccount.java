package br.com.fza.paymentchallenge.rest.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSameAccountValidator.class)
public @interface NotSameAccount {

    String message() default "sourceAccountNumber and targetAccountNumber must be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
