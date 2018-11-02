package br.com.fza.paymentchallenge.rest.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
@ApiModel(value = "accountResponse", description = "Contains the account name and the current balance")
public class AccountResponse implements Serializable {

    private final Long number;

    private final String name;

    private final BigDecimal currentBalance;

}