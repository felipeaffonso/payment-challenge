package br.com.fza.paymentchallenge.services.impl;

import br.com.fza.paymentchallenge.exceptions.AccountNotFoundException;
import br.com.fza.paymentchallenge.exceptions.CouldNotCreateAccountException;
import br.com.fza.paymentchallenge.exceptions.CouldNotDeleteAccountException;
import br.com.fza.paymentchallenge.exceptions.CouldNotFindAccountException;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.repository.AccountRepository;
import br.com.fza.paymentchallenge.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceMysqlImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(final Account account) {
        try{
            return this.accountRepository.save(account);
        } catch (final Exception e) {
            throw new CouldNotCreateAccountException("The account could not be created.", e);
        }
    }

    @Override
    public void deleteAccount(final Long id) {
        try {
            final boolean accountExists = this.accountRepository.existsById(id);
            if (accountExists) {
                this.accountRepository.delete(Account.builder().id(id).build());
            } else {
                throw new AccountNotFoundException("The account id " + id + " does not exists");
            }
        } catch(final AccountNotFoundException e) {
            throw e;
        } catch(final Exception e) {
            throw new CouldNotDeleteAccountException("The account " + id + " could not be deleted.", e);
        }
    }

    @Override
    public Optional<Account> findAccount(final Long id) {
        try {
            return this.accountRepository.findById(id);
        } catch(final Exception e) {
            throw new CouldNotFindAccountException("Could not find Account with id: " + id, e);
        }
    }

    @Override
    public Collection<Account> findAllAccounts() {
        try {
            return this.accountRepository.findAll();
        } catch(final Exception e) {
            throw new CouldNotFindAccountException("Could not find accounts.", e);
        }
    }
}