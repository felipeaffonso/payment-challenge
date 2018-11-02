package br.com.fza.paymentchallenge.utils;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilsTest {

    @Test
    public void timestampToLocalDate() {
        //given
        final Timestamp timestamp = new Timestamp(now().atStartOfDay(ZoneOffset.systemDefault().normalized()).toInstant().toEpochMilli());

        //when
        final LocalDate result = DateUtils.timestampToLocalDate(timestamp);

        //then
        final LocalDate expectedLocalDate = now(ZoneOffset.systemDefault().normalized());
        assertThat(result)
                .isEqualTo(expectedLocalDate);
    }

    @Test
    public void timestampToLocalDateMustReturnNullWithNullArgument() {
        //given
        final Timestamp timestamp = null;

        //when
        final LocalDate result = DateUtils.timestampToLocalDate(timestamp);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void localDateToDate() {
        //given
        final LocalDate localDate = now(ZoneOffset.systemDefault().normalized());

        //when
        final Date result = DateUtils.localDateToDate(localDate);

        //then
        final Date expectedDate = Date.from(now().atStartOfDay(ZoneOffset.systemDefault().normalized()).toInstant());
        assertThat(result)
                .isEqualTo(expectedDate);
    }

    @Test
    public void localDateToDateMustReturnNullWithNullArgument() {
        //given
        final LocalDate localDate = null;

        //when
        final Date result = DateUtils.localDateToDate(localDate);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void localDateTimeToTimestamp() {
        //given
        final LocalDateTime localDateTime = now().atStartOfDay();

        //when
        final Timestamp result = DateUtils.localDateTimeToTimestamp(localDateTime);

        //then
        final Timestamp expectedTimestamp = new Timestamp(now()
                .atStartOfDay(ZoneOffset.systemDefault().normalized()).toInstant().toEpochMilli());
        assertThat(result)
                .isEqualTo(expectedTimestamp);
    }

    @Test
    public void localDateTimeToTimestampMustReturnNullWithNullArgument() {
        //given
        final LocalDateTime localDateTime = null;

        //when
        final Timestamp result = DateUtils.localDateTimeToTimestamp(localDateTime);

        //then
        assertThat(result)
                .isNull();
    }

    @Test
    public void localDateToTimestamp() {
        //given
        final LocalDate localDate = now(ZoneOffset.systemDefault().normalized());

        //when
        final Timestamp result = DateUtils.localDateToTimestamp(localDate);

        //then
        final Timestamp expectedTimeStamp =
                new Timestamp(now().atStartOfDay(ZoneOffset.systemDefault().normalized()).toInstant().toEpochMilli());

        assertThat(result)
                .isEqualTo(expectedTimeStamp);
    }

    @Test
    public void localDateToTimestampMustReturnNullWithNullArgument() {
        //given
        final LocalDate localDate = null;

        //when
        final Timestamp result = DateUtils.localDateToTimestamp(localDate);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void timestampToLocalDateTime() {
        //given
        final Timestamp timestamp = new Timestamp(now()
                .atStartOfDay(ZoneOffset.systemDefault().normalized()).toInstant().toEpochMilli());

        //when
        final LocalDateTime result = DateUtils.timestampToLocalDateTime(timestamp);

        //then
        final LocalDateTime expectedLocalDateTime = now().atStartOfDay();
        assertThat(result)
                .isEqualTo(expectedLocalDateTime);
    }

    @Test
    public void timestampToLocalDateTimeMustReturnNullWithNullArgument() {
        //given
        final Timestamp timestamp = null;

        //when
        final LocalDateTime result = DateUtils.timestampToLocalDateTime(timestamp);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void stringToLocalDateTime() {
        //given
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("+yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.systemDefault());
        final LocalDateTime now = LocalDateTime.now();
        final String stringToLocalDateTime = now.format(formatter);

        //when
        final LocalDateTime result = DateUtils.stringToLocalDateTime(stringToLocalDateTime);

        //then
        assertThat(result).isEqualToIgnoringNanos(now);
    }

    @Test
    public void stringToLocalDateTimeMustReturnNullWithNullArgument() {
        //given
        final String stringToLocalDateTime = null;

        //when
        final LocalDateTime result = DateUtils.stringToLocalDateTime(stringToLocalDateTime);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void localDateTimeToStringPtBr() {
        //given
        final LocalDateTime localDateTime = LocalDateTime.now();

        //when
        final String result = DateUtils.localDateTimeToStringPtBr(localDateTime);

        //then
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        assertThat(result)
                .isEqualTo(localDateTime.format(formatter));
    }

    @Test
    public void localDateTimeToStringPtBrMustResturNullWithNullArgument() {
        //given
        final LocalDateTime localDateTime = null;

        //when
        final String result = DateUtils.localDateTimeToStringPtBr(localDateTime);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void stringToLocalDate() {
        //given
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("+yyyy-MM-dd")
                .withZone(ZoneId.systemDefault());
        final LocalDate now = LocalDate.now();
        final String stringToLocalDate = now.format(formatter);

        //when
        final LocalDate result = DateUtils.stringToLocalDate(stringToLocalDate);

        //then
        assertThat(result).isEqualTo(now);
    }

    @Test
    public void stringToLocalDateMustReturnNullWithNullArgument() {
        //given
        final String stringToLocalDate = null;

        //when
        final LocalDate result = DateUtils.stringToLocalDate(stringToLocalDate);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void isoStringToLocalDateTime() {
        //given
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        final LocalDateTime now = LocalDateTime.now();
        final String isoStringToLocalDateTime = now.format(formatter);

        //when
        final LocalDateTime result = DateUtils.isoStringToLocalDateTime(isoStringToLocalDateTime);

        //then
        assertThat(result).isEqualToIgnoringNanos(now);
    }

    @Test
    public void isoStringToLocalDateTimeMustReturnNullWithNullArgument() {
        //given
        final String isoStringToLocalDateTime = null;

        //when
        final LocalDateTime result = DateUtils.isoStringToLocalDateTime(isoStringToLocalDateTime);

        //then
        assertThat(result).isNull();
    }


    @Test
    public void getLocalDatesBetweenLocalDates() {
        //given
        final LocalDate start = now().minusDays(10);
        final LocalDate end = now().minusDays(8);

        //when
        final List<LocalDate> result = DateUtils.getLocalDatesBetween(start, end);

        //then
        final LocalDate expectedStart = now().minusDays(10);
        final LocalDate expectedEnd = now().minusDays(9);
        assertThat(result)
                .contains(expectedStart, expectedEnd)
                .hasSize(2);
    }

}