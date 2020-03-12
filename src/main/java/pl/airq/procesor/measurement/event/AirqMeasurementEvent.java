package pl.airq.procesor.measurement.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.procesor.measurement.payload.AirqMeasurementPayload;

@RegisterForReflection
public class AirqMeasurementEvent extends AirqEvent {

    @JsonCreator
    @JsonIgnoreProperties({"eventType"})
    public AirqMeasurementEvent(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                @JsonProperty("JsonProperty") AirqMeasurementPayload payload) {
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
