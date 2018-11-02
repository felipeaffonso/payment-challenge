package br.com.fza.paymentchallenge.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateTimeAttributeConverterTest {

    @InjectMocks
    private LocalDateTimeAttributeConverter target;

    @Test
    public void convertToDatabaseColumn() {
        //given
        final LocalDateTime localDateTime = now(ZoneOffset.systemDefault().normalized());

        //when
        final Timestamp result = target.convertToDatabaseColumn(localDateTime);

        //then
        final Timestamp expectedTimestamp = Timestamp.valueOf(localDateTime);

        assertThat(result)
                .isEqualTo(expectedTimestamp);
    }

    @Test
    public void convertToEntityAttribute() {
        //given
        final LocalDateTime now = now();
        final Timestamp timestamp = Timestamp.valueOf(now);

        //when
        final LocalDateTime result = target.convertToEntityAttribute(timestamp);

        //then
        final LocalDateTime expectedLocalDate = now;
        assertThat(result)
                .isEqualTo(expectedLocalDate);
    }

    @Test
    public void convertToDatabaseColumnNullValue() {
        //given
        final LocalDateTime localDateTime = null;

        //when
        final Timestamp result = target.convertToDatabaseColumn(localDateTime);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void convertToEntityAttributeNullValue() {
        //given
        final Timestamp timestamp = null;

        //when
        final LocalDateTime result = target.convertToEntityAttribute(timestamp);

        //then
        assertThat(result).isNull();
    }

}