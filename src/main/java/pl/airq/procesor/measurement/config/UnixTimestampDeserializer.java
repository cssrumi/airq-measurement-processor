package pl.airq.procesor.measurement.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UnixTimestampDeserializer extends JsonDeserializer<LocalDateTime> {
    private final Logger log = LoggerFactory.getLogger(UnixTimestampDeserializer.class);

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String timestamp = jp.getText().trim();
        try {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(timestamp + "000"));
            return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (NumberFormatException e) {
            log.warn("Unable to deserialize timestamp: " + timestamp, e);
            return null;
        }
    }
}
