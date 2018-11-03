package br.com.fza.paymentchallenge.model;

import br.com.fza.paymentchallenge.exceptions.NegativeAccountBalanceException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.internal.matchers.Null;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class AccountTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void giveMoneyMustThrowExceptionWhenAccountBalanceIsGoingNegative() {
        final Account account = Account.builder().name("Dummy").balance(BigDecimal.ONE).build();

        thrown.expect(NegativeAccountBalanceException.class);

        account.giveMoney(BigDecimal.TEN);
    }

    @Test
    public void giveMoneyMustUpdateBalanceWhenAccountBalanceIsGoingZero() {
        final Account account = Account.builder().name("Dummy").balance(BigDecimal.TEN).build();

        account.giveMoney(BigDecimal.TEN);

        assertThat(account.getBalance()).isZero();
    }

    @Test
    public void giveMoneyMustUpdateBalanceWhenAccountBalanceIsGoingPositive() {
        final Account account = Account.builder().name("Dummy").balance(BigDecimal.TEN).build();

        account.giveMoney(BigDecimal.ONE);

        assertThat(account.getBalance()).isEqualTo(BigDecimal.valueOf(9L));
    }

    @Test
    public void giveMoneyMustThrowExceptionWhenAmountIsNegative() {
        final Account account = Account.builder().name("Dummy").balance(BigDecimal.ONE).build();

        thrown.expect(IllegalArgumentException.class);

        account.giveMoney(BigDecimal.valueOf(-1L));
    }

    @Test
    public void giveMoneyMustThrowExceptionWhenAmountIsNull() {
        final Account account = Account.builder().name("Dummy").balance(BigDecimal.ONE).build();

        thrown.expect(NullPointerException.class);

        account.giveMoney(null);
    }

    @Test
    public void receiveMoney() {
    }
}