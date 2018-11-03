package br.com.fza.paymentchallenge.exceptions;

public class TransferNotFoundException extends RuntimeException {

    public TransferNotFoundException(final String message) {
        super(message);
    }

}