package br.com.fza.paymentchallenge.rest.converters;

import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.rest.response.AccountResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AccountConverterTest {

    @InjectMocks
    private AccountConverter target;

    @Test(expected = NullPointerException.class)
    public void convertNullValue() {
        this.target.convert(null);
    }

    @Test
    public void convert() {
        final Account account = Account.builder()
                .balance(TEN)
                .name("Dummy")
                .id(1L)
                .version(0)
                .build();

        final AccountResponse result = target.convert(account);

        final AccountResponse expectedAccountResponse = AccountResponse.builder()
                .currentBalance(TEN)
                .name("Dummy")
                .number(1L)
                .build();
        assertThat(result).isEqualToComparingFieldByField(expectedAccountResponse);
    }
}