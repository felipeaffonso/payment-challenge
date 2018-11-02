package br.com.fza.paymentchallenge.services;

import br.com.fza.paymentchallenge.model.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {

    Account createAccount(Account account);

    void deleteAccount(Long id);

    Optional<Account> findAccount(Long id);

    Collection<Account> findAllAccounts();

}