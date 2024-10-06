package za.ac.cput.util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeTypeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    private static final DateTimeFormatter formatterWithoutMillis = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final DateTimeFormatter formatterWithMillis1 = DateTimeFormatter.ofPattern("HH:mm:ss.S");
    private static final DateTimeFormatter formatterWithMillis2 = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
    private static final DateTimeFormatter formatterWithMillis3 = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Override
    public JsonElement serialize(LocalTime localTime, Type srcType, JsonSerializationContext context) {
        if (localTime.getNano() % 1_000_000 == 0) {  // No milliseconds
            return new JsonPrimitive(localTime.format(formatterWithoutMillis));
        } else {  // With milliseconds
            return new JsonPrimitive(localTime.format(formatterWithMillis3));
        }
    }

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String timeString = json.getAsString();
        try {
            return LocalTime.parse(timeString, formatterWithoutMillis);
        } catch (DateTimeParseException e1) {
            try {
                return LocalTime.parse(timeString, formatterWithMillis1);
            } catch (DateTimeParseException e2) {
                try {
                    return LocalTime.parse(timeString, formatterWithMillis2);
                } catch (DateTimeParseException e3) {
                    return LocalTime.parse(timeString, formatterWithMillis3);
                }
            }
        }
    }
}
