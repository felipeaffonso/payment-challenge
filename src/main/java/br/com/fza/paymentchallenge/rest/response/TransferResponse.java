package br.com.fza.paymentchallenge.rest.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@ApiModel(value = "transferResponse", description = "Contains the transfer and the involved accounts details")
public class TransferResponse {

    private Long transferNumber;

    private Long sourceAccountNumber;

    private Long targetAccountNumber;

    private BigDecimal transferredAmount;

    private LocalDateTime createdDate;

}