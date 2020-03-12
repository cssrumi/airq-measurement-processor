package pl.airq.procesor.measurement.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.procesor.measurement.payload.Payload;

@RegisterForReflection(methods = true)
public abstract class AirqEvent implements Event {

    final String eventType;
    final OffsetDateTime timestamp;
    final Payload payload;

    @JsonCreator
    AirqEvent(@JsonProperty("eventType") String eventType,
              @JsonProperty("timestamp") OffsetDateTime timestamp,
              @JsonProperty("payload") Payload payload) {
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.payload = payload;
    }



    public String getEventType() {
        return eventType;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public Payload getPayload() {
        return payload;
    }
}
