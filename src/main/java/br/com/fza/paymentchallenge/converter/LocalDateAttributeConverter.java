package br.com.fza.paymentchallenge.converter;


import br.com.fza.paymentchallenge.utils.DateUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(final LocalDate localDate) {
        return DateUtils.localDateToTimestamp(localDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(final Timestamp timestamp) {
        return DateUtils.timestampToLocalDate(timestamp);
    }

}