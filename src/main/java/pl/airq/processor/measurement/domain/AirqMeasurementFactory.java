package pl.airq.processor.measurement.domain;

import javax.enterprise.context.ApplicationScoped;
import pl.airq.common.domain.measurement.AirqMeasurement;
import pl.airq.common.domain.measurement.AirqMeasurementEvent;
import pl.airq.common.domain.measurement.AirqMeasurementPayload;

@ApplicationScoped
public class AirqMeasurementFactory {

    public AirqMeasurement from(AirqMeasurementEvent event) {
        AirqMeasurementPayload payload = event.payload;

        return new AirqMeasurement(
                event.timestamp,
                payload.temperature,
                payload.humidity,
                payload.pm10,
                payload.pm25,
                payload.stationId,
                payload.location
        );
    }

}
