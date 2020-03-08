package pl.airq.procesor.measurement.domain;

import java.time.LocalDateTime;
import pl.airq.procesor.measurement.vo.Measurement;
import pl.airq.procesor.measurement.vo.StationId;
import pl.airq.procesor.measurement.vo.StationLocation;

public final class AirqMeasurement {

    private final LocalDateTime timestamp;
    private final Measurement temperature;
    private final Measurement humidity;
    private final Measurement pm10;
    private final Measurement pm25;
    private final StationId stationId;
    private final StationLocation location;

    public AirqMeasurement(LocalDateTime timestamp, Measurement temperature, Measurement humidity, Measurement pm10,
                           Measurement pm25, StationId stationId, StationLocation location) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.stationId = stationId;
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Measurement getTemperature() {
        return temperature;
    }

    public Measurement getHumidity() {
        return humidity;
    }

    public Measurement getPm10() {
        return pm10;
    }

    public Measurement getPm25() {
        return pm25;
    }

    public StationId getStationId() {
        return stationId;
    }

    public StationLocation getLocation() {
        return location;
    }
}
