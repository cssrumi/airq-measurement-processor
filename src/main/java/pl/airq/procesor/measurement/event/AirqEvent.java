package pl.airq.procesor.measurement.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.procesor.measurement.payload.Payload;

@RegisterForReflection()
public abstract class AirqEvent implements Event {

    final String eventType;
    final OffsetDateTime timestamp;
    final Payload payload;

    @JsonCreator
    AirqEvent(String eventType, OffsetDateTime timestamp, Payload payload) {
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
