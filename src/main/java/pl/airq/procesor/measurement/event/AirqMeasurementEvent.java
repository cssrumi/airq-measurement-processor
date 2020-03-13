package pl.airq.procesor.measurement.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.procesor.measurement.payload.AirqMeasurementPayload;

@RegisterForReflection
public class AirqMeasurementEvent extends AirqEvent {

    AirqMeasurementEvent(OffsetDateTime timestamp, AirqMeasurementPayload payload) {
        super("AirqMeasurement", timestamp, payload);
        System.out.println("AirqMeasurementEvent constructor invoked");
    }

    @JsonCreator
    @JsonIgnoreProperties("eventType")
    public AirqMeasurementEvent from(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                     @JsonProperty("payload") AirqMeasurementPayload payload) {
        System.out.println("AirqMeasurementEvent Factory invoked");
        return new AirqMeasurementEvent(timestamp, payload);
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
