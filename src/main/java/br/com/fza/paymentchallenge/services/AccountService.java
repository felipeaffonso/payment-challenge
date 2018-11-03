package br.com.fza.paymentchallenge.services;

import br.com.fza.paymentchallenge.model.Account;

import java.util.Optional;

public interface AccountService {

    Account createAccount(Account account);

    Optional<Account> findAccount(Long id);

    Iterable<Account> findAllAccounts();

    Account updateAccount(Account sourceAccount);

}