package jw.fluent.api.utilites.files.json.adapters;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateTimeOffsetAdapter implements JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {
    @Override
    public OffsetDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("not a JSON object");
        }

        final JsonObject obj = (JsonObject) json;
        var timeStamp = obj.get("timeStamp").getAsLong();
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneOffset.UTC);
    }

    @Override
    public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext jsonSerializationContext) {

        final JsonObject obj = new JsonObject();
        obj.addProperty("timeStamp", offsetDateTime.toEpochSecond());
        return obj;
    }
}
