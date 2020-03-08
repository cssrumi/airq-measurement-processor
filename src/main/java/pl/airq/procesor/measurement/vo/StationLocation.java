package pl.airq.procesor.measurement.vo;

public class StationLocation {

    private String location;

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
