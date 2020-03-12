package pl.airq.procesor.measurement.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class StationId {

    private final String id;


    private StationId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @JsonCreator
    public static StationId from(@JsonProperty("id") String value) {
        return new StationId(value);
    }

    @Override
    public String toString() {
        return "StationId{" +
                "id='" + id + '\'' +
                '}';
    }

}
