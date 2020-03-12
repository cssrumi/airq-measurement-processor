package pl.airq.procesor.measurement.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.procesor.measurement.vo.Measurement;
import pl.airq.procesor.measurement.vo.StationId;
import pl.airq.procesor.measurement.vo.StationLocation;

@RegisterForReflection
public class AirqMeasurementPayload implements Payload {

    public Measurement temperature;
    public Measurement humidity;
    public Measurement pm10;
    public Measurement pm25;
    public StationId stationId;
    public StationLocation location;

    public AirqMeasurementPayload() {
    }

    @Override
    public String toString() {
        return "AirqMeasurementPayload{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", pm10=" + pm10 +
                ", pm25=" + pm25 +
                ", stationId=" + stationId +
                ", location=" + location +
                '}';
    }
}
