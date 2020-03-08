package pl.airq.procesor.measurement.vo;

public class StationId {

    private final String id;

    private StationId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static StationId from(String value) {
        return new StationId(value);
    }

    @Override
    public String toString() {
        return "StationId{" +
                "id='" + id + '\'' +
                '}';
    }

}
