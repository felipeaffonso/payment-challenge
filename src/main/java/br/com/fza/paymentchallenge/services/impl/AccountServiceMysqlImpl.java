package br.com.fza.paymentchallenge.services.impl;

import br.com.fza.paymentchallenge.exceptions.*;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.repository.AccountRepository;
import br.com.fza.paymentchallenge.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<Account> findAccount(final Long id) {
        try {
            return this.accountRepository.findById(id);
        } catch(final Exception e) {
            throw new CouldNotFindAccountException("Could not find Account with id: " + id, e);
        }
    }

    @Override
    public Iterable<Account> findAllAccounts() {
        try {
            return this.accountRepository.findAll();
        } catch(final Exception e) {
            throw new CouldNotFindAccountException("Could not find accounts.", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Account updateAccount(final Account account) {
        try{
            return this.accountRepository.save(account);
        } catch (final Exception e) {
            throw new CouldNotUpdateAccountException("The account " + account.getId() +  " could not be updated.", e);
        }
    }

}