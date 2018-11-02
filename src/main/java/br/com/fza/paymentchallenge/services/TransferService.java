package br.com.fza.paymentchallenge.services;

import br.com.fza.paymentchallenge.model.Transfer;

import java.math.BigDecimal;

public interface TransferService {

    Transfer createTransfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount);

}