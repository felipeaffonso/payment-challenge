package br.com.fza.paymentchallenge.rest.converters;

import br.com.fza.paymentchallenge.converter.Converter;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.rest.response.AccountResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<Account, AccountResponse> {

    @Override
    public AccountResponse convert(final @NonNull Account from) {
        return AccountResponse.builder()
                .number(from.getId())
                .name(from.getName())
                .currentBalance(from.getBalance())
                .build();
    }

}