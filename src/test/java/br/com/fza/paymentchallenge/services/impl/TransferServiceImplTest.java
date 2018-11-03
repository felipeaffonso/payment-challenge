package br.com.fza.paymentchallenge.services.impl;

import br.com.fza.paymentchallenge.exceptions.CouldNotFindTransferException;
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
import static org.assertj.core.api.Assertions.assertThat;
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
    public void createTransfer() {
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
                .name("Source")
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

        when(this.transferRepository.findById(id)).thenReturn(Optional.empty());

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