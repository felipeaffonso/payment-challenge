package br.com.fza.paymentchallenge.converter;


import br.com.fza.paymentchallenge.utils.DateUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(final LocalDateTime localDateTime) {
        return DateUtils.localDateTimeToTimestamp(localDateTime);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(final Timestamp timestamp) {
        return DateUtils.timestampToLocalDateTime(timestamp);
    }
}
