package by.it_academy.fitnessstudio.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class LongMillisToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {

    @Override
    public LocalDateTime convert(Long source) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneOffset.UTC);
    }
}
