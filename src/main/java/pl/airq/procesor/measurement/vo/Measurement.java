package pl.airq.procesor.measurement.vo;

public class Measurement {

    private final Double value;

    private Measurement(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public static Measurement from(Double value) {
        return new Measurement(value);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "value=" + value +
                '}';
    }
}
