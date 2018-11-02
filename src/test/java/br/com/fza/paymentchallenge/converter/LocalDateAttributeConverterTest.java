package br.com.fza.paymentchallenge.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateAttributeConverterTest {

    @InjectMocks
    private LocalDateAttributeConverter target;

    @Test
    public void convertToDatabaseColumn() {
        //given
        final LocalDate localDate = now(ZoneOffset.systemDefault().normalized());

        //when
        final Timestamp result = target.convertToDatabaseColumn(localDate);

        //then
        final Timestamp expectedTimestamp = new Timestamp(localDate.atStartOfDay(ZoneOffset.systemDefault().normalized()).toInstant().toEpochMilli());

        assertThat(result)
                .isEqualTo(expectedTimestamp);
    }

    @Test
    public void convertToEntityAttribute() {
        //given
        final Timestamp timestamp = new Timestamp(now().atStartOfDay(ZoneOffset.systemDefault().normalized()).toInstant().toEpochMilli());

        //when
        final LocalDate result = target.convertToEntityAttribute(timestamp);

        //then

        final LocalDate expectedLocalDate = LocalDate.now(ZoneOffset.systemDefault().normalized());
        assertThat(result)
                .isEqualTo(expectedLocalDate);
    }

}