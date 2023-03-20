package by.it_academy.audit.converters;

import com.fasterxml.jackson.core.JacksonException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@JsonComponent
public class StringToLDTJsonDeserializer extends StdDeserializer<LocalDateTime> {
    public StringToLDTJsonDeserializer() {
        this(null);
    }
    public StringToLDTJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String text = p.getText();
        long decode = Long.parseLong(text);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(decode), ZoneOffset.UTC);
    }
}
