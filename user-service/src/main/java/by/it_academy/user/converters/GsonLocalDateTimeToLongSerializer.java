package by.it_academy.user.converters;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GsonLocalDateTimeToLongSerializer implements JsonSerializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        long epochMilli = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        return new JsonPrimitive(epochMilli);
    }
}
