package pl.airq.procesor.measurement.event;

import java.time.OffsetDateTime;
import pl.airq.procesor.measurement.payload.Payload;

public abstract class AirqEvent implements Event {

    final String eventType;
    final OffsetDateTime timestamp;
    final Payload payload;

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
