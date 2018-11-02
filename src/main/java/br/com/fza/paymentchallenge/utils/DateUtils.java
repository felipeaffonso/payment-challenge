package br.com.fza.paymentchallenge.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {

    public static LocalDate timestampToLocalDate(final Timestamp timestamp) {
        if(timestamp == null)
            return  null;
        return timestamp.toLocalDateTime().toLocalDate();
    }

    public static Date localDateToDate(final LocalDate localDate) {
        if(localDate == null)
            return null;
        return Date.valueOf(localDate);
    }

    public static Timestamp localDateTimeToTimestamp(final LocalDateTime localDateTime) {
        if(localDateTime == null)
            return null;
        return Timestamp.valueOf(localDateTime);
    }

    public static Timestamp localDateToTimestamp(final LocalDate localDate) {
        if(localDate == null)
            return null;
        return Timestamp.valueOf(localDate.atStartOfDay());
    }

    public static LocalDateTime timestampToLocalDateTime(final Timestamp timestamp) {
        if(timestamp == null)
            return  null;
        return timestamp.toLocalDateTime();
    }

    public static LocalDateTime stringToLocalDateTime(final String dateString) {
        if(dateString == null) {
            return null;
        }
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("+yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.systemDefault());
        return LocalDateTime.parse(dateString, formatter);
    }

    public static String localDateTimeToStringPtBr(final LocalDateTime date) {
        if(date == null) {
            return null;
        }
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return date.format(formatter);
    }

    public static LocalDate stringToLocalDate(final String dateString) {
        if(dateString == null) {
            return null;
        }
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("+yyyy-MM-dd")
                .withZone(ZoneId.systemDefault());
        return LocalDate.parse(dateString, formatter);

    }

    public static LocalDateTime isoStringToLocalDateTime(final String dateTime) {
        if(StringUtils.isEmpty(dateTime))
            return null;
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
    }

    public static List<LocalDate> getLocalDatesBetween(final LocalDate startDate, final LocalDate endDate) {

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }

}