package pl.airq.procesor.measurement.event;

import java.time.LocalDateTime;
import pl.airq.procesor.measurement.payload.Payload;

public abstract class AirqEvent implements Event {

    final String eventType;
    final LocalDateTime timestamp;
    final Payload payload;

    AirqEvent(String eventType, LocalDateTime timestamp, Payload payload) {
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public Payload getPayload() {
        return payload;
    }
}
