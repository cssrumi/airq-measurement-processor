package pl.airq.procesor.measurement.domain;

import javax.inject.Singleton;
import pl.airq.procesor.measurement.event.AirqMeasurementEvent;
import pl.airq.procesor.measurement.payload.AirqMeasurementPayload;

@Singleton
public class AirqMeasurementFactory {

    public AirqMeasurement from(AirqMeasurementEvent event) {
        AirqMeasurementPayload payload = (AirqMeasurementPayload) event.getPayload();

        return new AirqMeasurement(
                event.getTimestamp(),
                payload.temperature,
                payload.humidity,
                payload.pm10,
                payload.pm25,
                payload.stationId,
                payload.location
        );
    }

}
