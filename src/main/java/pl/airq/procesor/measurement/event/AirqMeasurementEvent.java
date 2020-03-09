package pl.airq.procesor.measurement.event;

import java.time.OffsetDateTime;
import pl.airq.procesor.measurement.payload.AirqMeasurementPayload;

public class AirqMeasurementEvent extends AirqEvent {

    public AirqMeasurementEvent(OffsetDateTime timestamp, AirqMeasurementPayload payload) {
        super("AirqMeasurement", timestamp, payload);
    }

    @Override
    public String toString() {
        return "AirqMeasurementEvent{" +
                "eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp.toString() +
                ", payload=" + payload.toString() +
                '}';
    }
}
