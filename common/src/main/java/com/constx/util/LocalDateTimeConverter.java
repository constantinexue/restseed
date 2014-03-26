package com.constx.util;

import com.google.gson.*;
import org.joda.time.LocalDateTime;

import java.lang.reflect.Type;

public class LocalDateTimeConverter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
        return LocalDateTime.parse(json.getAsString());
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        String json = src.toString();
        return new JsonPrimitive(json);
    }

}
