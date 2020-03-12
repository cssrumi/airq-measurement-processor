package pl.airq.procesor.measurement.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class StationLocation {

    private final String location;

    private StationLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @JsonCreator
    public static StationLocation from(@JsonProperty("location") String value) {
        return new StationLocation(value);
    }

    @Override
    public String toString() {
        return "StationLocation{" +
                "location='" + location + '\'' +
                '}';
    }
}
