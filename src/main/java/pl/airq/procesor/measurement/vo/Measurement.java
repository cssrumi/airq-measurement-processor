package pl.airq.procesor.measurement.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Measurement {

    private final Double value;

    private Measurement(Double value) {
        this.value = value;
    }

    private Measurement(Integer value) {
        this.value = Double.valueOf(value);
    }

    public Double getValue() {
        return value;
    }

    @JsonCreator
    public static Measurement from(@JsonProperty("value") Double value) {
        return new Measurement(value);
    }

    @JsonCreator
    public static Measurement fromInteger(@JsonProperty("value") Integer value) {
        System.out.println("Hello from int factory");
        return new Measurement(value);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "value=" + value +
                '}';
    }
}
