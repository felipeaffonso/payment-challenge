package br.com.fza.paymentchallenge.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(final String message) {
        super(message);
    }

}