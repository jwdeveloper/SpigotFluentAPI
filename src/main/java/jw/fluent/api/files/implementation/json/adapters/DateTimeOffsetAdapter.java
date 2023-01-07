package jw.fluent.api.files.implementation.json.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeOffsetAdapter implements JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {

    private final DateTimeFormatter formatter;

    public DateTimeOffsetAdapter() {
        formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).optionalEnd()
                .appendPattern("x")
                .toFormatter();
    }

    @Override
    public OffsetDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("not a JSON object");
        }

        final JsonObject obj = (JsonObject) json;
        var timeStamp = obj.get("timeStamp");


        return OffsetDateTime.parse(timeStamp.getAsString(), this.formatter);
    }

    @Override
    public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext jsonSerializationContext) {

        final JsonObject obj = new JsonObject();
        obj.addProperty("timeStamp", offsetDateTime.format(this.formatter));
        return obj;
    }
}
