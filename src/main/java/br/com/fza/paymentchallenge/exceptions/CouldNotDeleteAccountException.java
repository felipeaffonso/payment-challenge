package br.com.fza.paymentchallenge.exceptions;

public class CouldNotDeleteAccountException extends RuntimeException {

    public CouldNotDeleteAccountException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
