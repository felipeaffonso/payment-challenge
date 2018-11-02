package br.com.fza.paymentchallenge.converter;

import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.rest.response.AccountResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<Account, AccountResponse> {

    @Override
    public AccountResponse convert(final Account from) {
        return AccountResponse.builder()
                .number(from.getId())
                .name(from.getName())
                .currentBalance(from.getBalance())
                .build();
    }

}