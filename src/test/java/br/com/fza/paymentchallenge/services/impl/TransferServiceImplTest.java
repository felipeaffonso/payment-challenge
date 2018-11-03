package br.com.fza.paymentchallenge.services.impl;

import br.com.fza.paymentchallenge.exceptions.AccountNotFoundException;
import br.com.fza.paymentchallenge.exceptions.CouldNotCreateTransferException;
import br.com.fza.paymentchallenge.exceptions.CouldNotFindTransferException;
import br.com.fza.paymentchallenge.exceptions.NegativeAccountBalanceException;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.model.Transfer;
import br.com.fza.paymentchallenge.repository.AccountRepository;
import br.com.fza.paymentchallenge.repository.TransferRepository;
import br.com.fza.paymentchallenge.services.AccountService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceImplTest {

    @Mock
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferServiceImpl target;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createTransferMustThrowExceptionWhenSourceAccountNotExists() {
        final Long sourceAccountId = 777L;
        final Long targetAccountId = 1L;

        when(this.accountService.findAccount(sourceAccountId)).thenReturn(empty());

        thrown.expect(AccountNotFoundException.class);
        thrown.expectMessage("The source account " + sourceAccountId + " does not exists.");

        this.target.createTransfer(sourceAccountId, targetAccountId, TEN);
    }

    @Test
    public void createTransferMustThrowExceptionWhenTargetAccountNotExists() {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 777L;

        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .id(sourceAccountId)
                .name("Source")
                .version(0)
                .build();

        when(this.accountService.findAccount(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(this.accountService.findAccount(targetAccountId)).thenReturn(empty());

        thrown.expect(AccountNotFoundException.class);
        thrown.expectMessage("The target account " + targetAccountId + " does not exists.");

        this.target.createTransfer(sourceAccountId, targetAccountId, TEN);
    }

    @Test
    public void createTransferMustThrowExceptionWhenSourceAccountGoesNegative() {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 2L;

        final Account sourceAccount = Account.builder()
                .balance(ZERO)
                .id(sourceAccountId)
                .name("Source")
                .version(0)
                .build();

        final Account targetAccount = Account.builder()
                .balance(TEN)
                .id(targetAccountId)
                .name("Target")
                .version(0)
                .build();

        when(this.accountService.findAccount(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(this.accountService.findAccount(targetAccountId)).thenReturn(Optional.of(targetAccount));

        thrown.expect(NegativeAccountBalanceException.class);

        this.target.createTransfer(sourceAccountId, targetAccountId, TEN);
    }

    @Test
    public void createTransferMustThrowExceptionWhenSourceAccountSaveFails() {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 2L;

        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .id(sourceAccountId)
                .name("Source")
                .version(0)
                .build();

        final Account targetAccount = Account.builder()
                .balance(TEN)
                .id(targetAccountId)
                .name("Target")
                .version(0)
                .build();

        when(this.accountService.findAccount(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(this.accountService.findAccount(targetAccountId)).thenReturn(Optional.of(targetAccount));

        final Throwable exception = new IllegalStateException();

        when(this.accountRepository.save(sourceAccount)).thenThrow(exception);

        thrown.expect(CouldNotCreateTransferException.class);
        thrown.expectMessage("Could not create the " + TEN
                + " Euros transfer between accounts "
                + sourceAccountId +
                " and " + targetAccountId);

        this.target.createTransfer(sourceAccountId, targetAccountId, TEN);
    }

    @Test
    public void createTransferMustThrowExceptionWheTargetAccountSaveFails() {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 2L;

        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .id(sourceAccountId)
                .name("Source")
                .version(0)
                .build();

        final Account targetAccount = Account.builder()
                .balance(TEN)
                .id(targetAccountId)
                .name("Target")
                .version(0)
                .build();

        when(this.accountService.findAccount(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(this.accountService.findAccount(targetAccountId)).thenReturn(Optional.of(targetAccount));

        final Throwable exception = new IllegalStateException();

        when(this.accountRepository.save(sourceAccount)).thenReturn(sourceAccount);
        when(this.accountRepository.save(targetAccount)).thenThrow(exception);

        thrown.expect(CouldNotCreateTransferException.class);
        thrown.expectMessage("Could not create the " + TEN
                + " Euros transfer between accounts "
                + sourceAccountId +
                " and " + targetAccountId);

        this.target.createTransfer(sourceAccountId, targetAccountId, TEN);
    }

    @Test
    public void createTransferMustReturnPersistedTransferWithValidInput() {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 2L;

        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .id(sourceAccountId)
                .name("Source")
                .version(0)
                .build();

        final Account targetAccount = Account.builder()
                .balance(TEN)
                .id(targetAccountId)
                .name("Target")
                .version(0)
                .build();

        when(this.accountService.findAccount(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(this.accountService.findAccount(targetAccountId)).thenReturn(Optional.of(targetAccount));

        when(this.accountRepository.save(sourceAccount)).thenReturn(sourceAccount);
        when(this.accountRepository.save(targetAccount)).thenReturn(targetAccount);

        final Transfer persistedTransfer = Transfer.builder()
                .amount(TEN)
                .source(sourceAccount)
                .target(targetAccount)
                .id(1L)
                .build();

        when(this.transferRepository.save(any(Transfer.class))).thenReturn(persistedTransfer);

        final Optional<Transfer> result = this.target.createTransfer(sourceAccountId, targetAccountId, TEN);

        assertThat(result).isNotEmpty().contains(persistedTransfer);
    }

    @Test
    public void createTransferMustThrowExceptionWhenTransferSaveFails() {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 2L;

        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .id(sourceAccountId)
                .name("Source")
                .version(0)
                .build();

        final Account targetAccount = Account.builder()
                .balance(TEN)
                .id(targetAccountId)
                .name("Target")
                .version(0)
                .build();

        when(this.accountService.findAccount(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(this.accountService.findAccount(targetAccountId)).thenReturn(Optional.of(targetAccount));

        when(this.accountRepository.save(sourceAccount)).thenReturn(sourceAccount);
        when(this.accountRepository.save(targetAccount)).thenReturn(targetAccount);

        final Throwable exception = new IllegalStateException();
        when(this.transferRepository.save(any(Transfer.class))).thenThrow(exception);

        thrown.expect(CouldNotCreateTransferException.class);
        thrown.expectMessage("Could not create the " + TEN
                + " Euros transfer between accounts "
                + sourceAccountId +
                " and " + targetAccountId);

        this.target.createTransfer(sourceAccountId, targetAccountId, TEN);
    }

    @Test
    public void findTransferMustReturnTransferWhenItExists() {
        final Long id = 1L;
        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .id(id)
                .name("Source")
                .version(0)
                .build();
        final Account targetAccount = Account.builder()
                .balance(TEN)
                .id(id)
                .name("Target")
                .version(0)
                .build();

        final Transfer persistedTransfer = Transfer.builder()
                .id(id)
                .amount(TEN)
                .source(sourceAccount)
                .target(targetAccount)
                .build();

        when(this.transferRepository.findById(id)).thenReturn(Optional.of(persistedTransfer));

        final Optional<Transfer> result = target.findTransfer(id);

        assertThat(result).isNotEmpty().contains(persistedTransfer);
    }

    @Test
    public void findTransferMustReturnEmptyWhenTransferNotExists() {
        final Long id = 1L;

        when(this.transferRepository.findById(id)).thenReturn(empty());

        final Optional<Transfer> result = target.findTransfer(id);

        assertThat(result).isEmpty();
    }

    @Test
    public void findTransferMustThrowExceptionWhenRepositoryThrowsException() {
        final Long id = 1L;
        final Throwable exception = new IllegalArgumentException();

        when(this.transferRepository.findById(id)).thenThrow(exception);

        thrown.expect(CouldNotFindTransferException.class);
        thrown.expectMessage("Could not find Transfer with id: " + id);

        this.target.findTransfer(id);
    }

}