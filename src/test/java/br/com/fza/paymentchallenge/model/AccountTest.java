package br.com.fza.paymentchallenge.model;

import br.com.fza.paymentchallenge.exceptions.NegativeAccountBalanceException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class AccountTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void giveMoneyMustThrowExceptionWhenAccountBalanceIsGoingNegative() {
        final Account account = Account.builder().name("Dummy").balance(ONE).build();

        thrown.expect(NegativeAccountBalanceException.class);

        account.giveMoney(TEN);
    }

    @Test
    public void giveMoneyMustUpdateBalanceWhenAccountBalanceIsGoingZero() {
        final Account account = Account.builder().name("Dummy").balance(TEN).build();

        account.giveMoney(TEN);

        assertThat(account.getBalance()).isZero();
    }

    @Test
    public void giveMoneyMustUpdateBalanceWhenAccountBalanceIsGoingPositive() {
        final Account account = Account.builder().name("Dummy").balance(TEN).build();

        account.giveMoney(ONE);

        assertThat(account.getBalance()).isEqualTo(BigDecimal.valueOf(9L));
    }

    @Test
    public void giveMoneyMustThrowExceptionWhenAmountIsNegative() {
        final Account account = Account.builder().name("Dummy").balance(ONE).build();

        thrown.expect(IllegalArgumentException.class);

        account.giveMoney(BigDecimal.valueOf(-1L));
    }

    @Test
    public void giveMoneyMustThrowExceptionWhenAmountIsNull() {
        final Account account = Account.builder().name("Dummy").balance(ONE).build();

        thrown.expect(NullPointerException.class);

        account.giveMoney(null);
    }

    @Test
    public void receiveMoneyMustUpdateBalanceWhenValueIsPositive() {
        final Account account = Account.builder().name("Dummy").balance(ONE).build();

        account.receiveMoney(TEN);

        assertThat(account.getBalance()).isEqualTo(BigDecimal.valueOf(11L));
    }

    @Test
    public void receiveMoneyMustThrowExceptionWhenValueIsNegative() {
        final Account account = Account.builder().name("Dummy").balance(ONE).build();

        thrown.expect(IllegalArgumentException.class);

        account.receiveMoney(BigDecimal.valueOf(-1L));
    }

    @Test
    public void receiveMoneyMustBeTheSameWhenValueIsZero() {
        final Account account = Account.builder().name("Dummy").balance(ONE).build();

        account.receiveMoney(BigDecimal.ZERO);

        assertThat(account.getBalance()).isEqualTo(ONE);
    }

    @Test
    public void callVersion() {
        final Account account = Account.builder().name("Dummy").balance(ONE).version(0).build();

        final Integer version = account.getVersion();

        assertThat(version).isEqualTo(0);
    }

    @Test
    public void callName() {
        final Account account = Account.builder().name("Dummy").balance(ONE).version(0).build();

        final String name = account.getName();

        assertThat(name).isEqualTo("Dummy");
    }

    @Test
    public void callBalance() {
        final Account account = Account.builder().name("Dummy").balance(ONE).version(0).build();

        final BigDecimal balance = account.getBalance();

        assertThat(balance).isEqualTo(ONE);
    }

}