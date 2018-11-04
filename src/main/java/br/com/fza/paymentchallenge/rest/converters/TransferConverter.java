package br.com.fza.paymentchallenge.rest.converters;

import br.com.fza.paymentchallenge.converter.Converter;
import br.com.fza.paymentchallenge.model.Transfer;
import br.com.fza.paymentchallenge.rest.response.TransferResponse;
import org.springframework.stereotype.Component;

@Component
public class TransferConverter implements Converter<Transfer, TransferResponse> {

    @Override
    public TransferResponse convert(final Transfer from) {
        return TransferResponse.builder()
                .transferNumber(from.getId())
                .sourceAccountNumber(from.getSource().getId())
                .targetAccountNumber(from.getTarget().getId())
                .transferredAmount(from.getAmount())
                .createdDate(from.getCreatedAt())
                .build();
    }

}
