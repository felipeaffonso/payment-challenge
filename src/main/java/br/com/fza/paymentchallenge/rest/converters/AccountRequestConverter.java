package br.com.fza.paymentchallenge.rest.converters;

import br.com.fza.paymentchallenge.converter.Converter;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.rest.request.AccountRequest;
import org.springframework.stereotype.Component;

@Component
public class AccountRequestConverter implements Converter<AccountRequest, Account> {

    @Override
    public Account convert(final AccountRequest from) {
        return Account.builder()
                .name(from.getName())
                .balance(from.getInitialBalance())
                .build();
    }
}
