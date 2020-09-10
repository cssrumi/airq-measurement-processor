package pl.airq.procesor.measurement.domain;

import javax.inject.Singleton;
import pl.airq.common.domain.event.AirqMeasurementEvent;
import pl.airq.common.domain.event.AirqMeasurementPayload;
import pl.airq.common.domain.measurement.AirqMeasurement;

@Singleton
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
