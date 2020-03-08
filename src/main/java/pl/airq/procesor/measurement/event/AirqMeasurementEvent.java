package pl.airq.procesor.measurement.event;

import java.time.LocalDateTime;
import pl.airq.procesor.measurement.payload.AirqMeasurementPayload;

public class AirqMeasurementEvent extends AirqEvent {

    public AirqMeasurementEvent(LocalDateTime timestamp, AirqMeasurementPayload payload) {
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
