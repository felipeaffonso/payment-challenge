package br.com.fza.paymentchallenge.rest.validators;

import br.com.fza.paymentchallenge.rest.request.TransferRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotSameAccountValidator implements ConstraintValidator<NotSameAccount, TransferRequest> {
    @Override
    public void initialize(final NotSameAccount constraintAnnotation) {

    }

    @Override
    public boolean isValid(final TransferRequest transfer, final ConstraintValidatorContext constraintValidatorContext) {
        return !transfer.getSourceAccountNumber().equals(transfer.getTargetAccountNumber());
    }

}