package br.com.fza.paymentchallenge.exceptions;

public class NegativeAccountBalanceException extends RuntimeException {

    public NegativeAccountBalanceException() {
        super("After this transfer the balance is going to be negative. " +
                "Impossible to transfer from this account");
    }

}