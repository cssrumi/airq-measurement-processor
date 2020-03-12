package pl.airq.procesor.measurement.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class StationLocation {

    private final String location;

    @JsonCreator
    private StationLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public static StationLocation from(String value) {
        return new StationLocation(value);
    }

    @Override
    public String toString() {
        return "StationLocation{" +
                "location='" + location + '\'' +
                '}';
    }
}
