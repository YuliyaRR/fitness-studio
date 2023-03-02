package by.it_academy.fitnessstudio.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Component
public class LocalDateTimeToLongMillisConverter implements Converter<LocalDateTime, Long> {

    @Override
    public Long convert(LocalDateTime source) {
        return source.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
