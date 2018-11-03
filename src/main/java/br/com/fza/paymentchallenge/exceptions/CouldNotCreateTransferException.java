package br.com.fza.paymentchallenge.exceptions;

import br.com.fza.paymentchallenge.rest.request.TransferRequest;

import java.math.BigDecimal;

public class CouldNotCreateTransferException extends RuntimeException {
    public CouldNotCreateTransferException(final Long sourceAccountId,
                                           final Long targetAccountId,
                                           final BigDecimal amount,
                                           final Throwable cause) {
        super("Could not create the " + amount
                + " Euros transfer between accounts "
                + sourceAccountId + " and " + targetAccountId, cause);
    }

    public CouldNotCreateTransferException(TransferRequest transferRequest) {
        super("Could not create the " + transferRequest.getAmount()
                + " Euros transfer between accounts "
                + transferRequest.getSourceAccountNumber() +
                " and " + transferRequest.getTargetAccountNumber());
    }
}