package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static ru.javawebinar.topjava.util.DateTimeUtil.ISO_DATE_TIME_FORMATTER;

/**
 * MrArtemAA
 * 15.05.2017
 */
public class DateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateTimeFormat> {


    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> set = new HashSet<>();
        set.add(LocalDateTime.class);
        return set;
    }

    @Override
    public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
        return new DateTimeFormatter();
    }

    @Override
    public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
        return new DateTimeFormatter();
    }

    private static class DateTimeFormatter implements Formatter<LocalDateTime> {

        @Override
        public LocalDateTime parse(String text, Locale locale) throws ParseException {
            return LocalDateTime.parse(text, ISO_DATE_TIME_FORMATTER);
        }

        @Override
        public String print(LocalDateTime object, Locale locale) {
            return object.format(ISO_DATE_TIME_FORMATTER);
        }
    }

}
