package br.com.fza.paymentchallenge.services.impl;

import br.com.fza.paymentchallenge.exceptions.CouldNotCreateAccountException;
import br.com.fza.paymentchallenge.exceptions.CouldNotFindAccountException;
import br.com.fza.paymentchallenge.exceptions.CouldNotUpdateAccountException;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.repository.AccountRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl target;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createAccountMustReturnPersistedAccount() {
        final Account account = Account.builder().name("Dummy").balance(TEN).build();

        final Account persistedAccount = Account.builder()
                .name("Dummy")
                .balance(TEN)
                .version(0)
                .id(1L)
                .build();

        persistedAccount.setCreatedAt(now());
        persistedAccount.setUpdatedAt(now());

        when(this.accountRepository.save(account)).thenReturn(persistedAccount);

        final Account createdAccount = target.createAccount(account);

        assertThat(createdAccount).isEqualTo(persistedAccount);
    }

    @Test
    public void createAccountMustReturnExceptionWhenAnExceptionIsThrowAtRepository() {
        final Account account = Account.builder().name("Dummy").balance(TEN).build();
        final Throwable exception = new IllegalArgumentException();

        when(this.accountRepository.save(account)).thenThrow(exception);

        thrown.expectMessage("The account could not be created.");
        thrown.expect(CouldNotCreateAccountException.class);

        target.createAccount(account);
    }

    @Test
    public void findAccountMustReturnAccountWithValidId() {
        final Long id = 1L;
        final Account persistedAccount = Account.builder()
                .name("Dummy")
                .balance(TEN)
                .version(0)
                .id(1L)
                .build();

        persistedAccount.setCreatedAt(now());
        persistedAccount.setUpdatedAt(now());

        when(this.accountRepository.findById(id)).thenReturn(Optional.of(persistedAccount));

        final Optional<Account> result = target.findAccount(id);

        assertThat(result).isNotEmpty().contains(persistedAccount);
    }

    @Test
    public void findAccountMustEmptyAccountWithValidId() {
        final Long id = 1L;

        when(this.accountRepository.findById(id)).thenReturn(Optional.empty());

        final Optional<Account> result = target.findAccount(id);

        assertThat(result).isEmpty();
    }


    @Test
    public void findAccountMustThrowExceptionWhenAnExceptionIsThrowOnRepository() {
        final Long id = 1L;
        final Throwable exception = new IllegalArgumentException();

        when(this.accountRepository.findById(id)).thenThrow(exception);

        thrown.expect(CouldNotFindAccountException.class);
        thrown.expectMessage("Could not find Account with id: " + id);

        target.findAccount(id);

    }

    @Test
    public void findAllAccountsMustReturnValues() {
        final Account persistedAccount = Account.builder()
                .name("Dummy")
                .balance(TEN)
                .version(0)
                .id(1L)
                .build();

        persistedAccount.setCreatedAt(now());
        persistedAccount.setUpdatedAt(now());

        when(this.accountRepository.findAll()).thenReturn(singletonList(persistedAccount));

        final Iterable<Account> result = target.findAllAccounts();

        assertThat(result).hasSize(1).contains(persistedAccount);
    }

    @Test
    public void findAllAccountsMustReturnEmptyResult() {
        when(this.accountRepository.findAll()).thenReturn(emptyList());

        final Iterable<Account> result = target.findAllAccounts();

        assertThat(result).hasSize(0);
    }

    @Test
    public void findAllAccountsMustThrowExceptionWhenRepositoryThrowsException() {
        final Throwable exception = new NullPointerException();
        when(this.accountRepository.findAll()).thenThrow(exception);

        thrown.expect(CouldNotFindAccountException.class);
        thrown.expectMessage("Could not find accounts.");

        target.findAllAccounts();
    }

    @Test
    public void updateAccountMustReturnPersistedAccount() {
        final Account account = Account.builder()
                .name("Dummy")
                .balance(ZERO)
                .version(0)
                .id(1L)
                .build();

        final LocalDateTime createdAt = now().minusDays(1);
        account.setCreatedAt(createdAt);
        account.setUpdatedAt(now().minusDays(1));

        final Account persistedAccount = Account.builder()
                .name("Dummy")
                .balance(TEN)
                .version(0)
                .id(1L)
                .build();

        persistedAccount.setCreatedAt(createdAt);
        persistedAccount.setUpdatedAt(now());

        when(this.accountRepository.save(account)).thenReturn(persistedAccount);

        final Account result = target.updateAccount(account);

        assertThat(result.getBalance()).isEqualTo(TEN);
        assertThat(result.getCreatedAt()).isEqualTo(createdAt);
        assertThat(result.getUpdatedAt()).isAfter(account.getUpdatedAt());
    }

    @Test
    public void updateAccountMustThrowExceptionWhenRepositoryThrowsException() {
        final Account account = Account.builder()
                .name("Dummy")
                .balance(ZERO)
                .version(0)
                .id(1L)
                .build();

        final Throwable exception = new IllegalArgumentException();

        when(this.accountRepository.save(account)).thenThrow(exception);

        thrown.expect(CouldNotUpdateAccountException.class);
        thrown.expectMessage("The account " + account.getId() + " could not be updated.");

        target.updateAccount(account);
    }

}