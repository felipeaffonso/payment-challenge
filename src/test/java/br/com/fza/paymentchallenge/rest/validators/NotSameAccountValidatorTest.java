package br.com.fza.paymentchallenge.rest.validators;

import br.com.fza.paymentchallenge.rest.request.TransferRequest;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

public class NotSameAccountValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void isValidMustFailWithSameAccounts() {
        final TransferRequest transferRequest = new TransferRequest(1L, 1L, TEN);

        final Set<ConstraintViolation<TransferRequest>> violations = validator.validate(transferRequest);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void isValidWithDifferentAccounts() {
        final TransferRequest transferRequest = new TransferRequest(1L, 2L, TEN);

        final Set<ConstraintViolation<TransferRequest>> violations = validator.validate(transferRequest);

        assertThat(violations).hasSize(0);
    }
}