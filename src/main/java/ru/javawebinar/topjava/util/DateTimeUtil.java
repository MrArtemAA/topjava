package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static boolean isBetween(Comparable dt, Comparable startDt, Comparable endDt) {
        return dt.compareTo(startDt) >= 0 && dt.compareTo(endDt) <= 0;
    }

    public static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        return isBetween(date, start, end);
    }

    public static boolean isBetween(LocalTime time, LocalTime start, LocalTime end) {
        return isBetween(time, start, end);
    }

    public static LocalDate parseDate(String stringDate) {
        if (stringDate == null || stringDate.equals(""))
            return null;
        else
            return LocalDate.parse(stringDate);
    }

    public static LocalTime parseTime(String stringTime) {
        if (stringTime == null || stringTime.equals(""))
            return null;
        else
            return LocalTime.parse(stringTime);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
