package br.com.fza.paymentchallenge.rest.converters;

import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.model.Transfer;
import br.com.fza.paymentchallenge.rest.response.TransferResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static java.math.BigDecimal.TEN;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TransferConverterTest {

    @InjectMocks
    private TransferConverter target;


    @Test(expected = NullPointerException.class)
    public void convertNullValue() {
        target.convert(null);
    }

    @Test
    public void convert() {
        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .name("Felipe")
                .id(1L)
                .version(0)
                .build();

        final Account targetAccount = Account.builder()
                .balance(TEN)
                .name("Nicolas")
                .id(1L)
                .version(0)
                .build();

        final Transfer transfer = Transfer.builder()
                .target(targetAccount)
                .source(sourceAccount)
                .amount(TEN)
                .id(10L)
                .build();

        final LocalDateTime createdAt = now();
        final LocalDateTime updatedAt = createdAt.plusDays(1);
        transfer.setCreatedAt(createdAt);
        transfer.setUpdatedAt(updatedAt);

        final TransferResponse result = target.convert(transfer);

        final TransferResponse expectedResponse = TransferResponse.builder()
                .createdDate(createdAt)
                .sourceAccountNumber(sourceAccount.getId())
                .targetAccountNumber(targetAccount.getId())
                .transferredAmount(TEN)
                .transferNumber(10L)
                .build();

        assertThat(result).isEqualTo(expectedResponse);
    }
}