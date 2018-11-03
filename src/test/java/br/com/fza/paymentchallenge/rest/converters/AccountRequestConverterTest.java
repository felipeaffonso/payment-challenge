package br.com.fza.paymentchallenge.rest.converters;

import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.rest.request.AccountRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AccountRequestConverterTest {

    @InjectMocks
    private AccountRequestConverter target;

    @Test(expected = NullPointerException.class)
    public void convertNullValue() {
        target.convert(null);
    }

    @Test
    public void convert() {
        final AccountRequest accountRequest = new AccountRequest("Dummy", TEN);

        final Account result = target.convert(accountRequest);

        final Account expectedAccount = Account.builder().balance(TEN).name("Dummy").build();

        assertThat(result).isEqualTo(expectedAccount);
    }
}