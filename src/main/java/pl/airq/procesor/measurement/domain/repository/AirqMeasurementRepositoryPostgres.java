package pl.airq.procesor.measurement.domain.repository;

import io.vertx.axle.pgclient.PgPool;
import io.vertx.axle.sqlclient.Tuple;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.airq.procesor.measurement.domain.AirqMeasurement;

@ApplicationScoped
public class AirqMeasurementRepositoryPostgres implements AirqMeasurementRepository {

    static final String SCHEMA = "CREATE TABLE IF NOT EXISTS AIRQ_MEASUREMENT\n" +
            "(\n" +
            "    id          BIGSERIAL PRIMARY KEY,\n" +
            "    timestamp   TIMESTAMPTZ,\n" +
            "    temperature DOUBLE PRECISION,\n" +
            "    humidity    DOUBLE PRECISION,\n" +
            "    pm10        DOUBLE PRECISION,\n" +
            "    pm25        DOUBLE PRECISION,\n" +
            "    stationId   VARCHAR(50),\n" +
            "    location    VARCHAR(100)\n" +
            ");";
    final String INSERT_QUERY = "INSERT INTO AIRQ_MEASUREMENT (timestamp, temperature, humidity, pm10, pm25, stationId, location) VALUES ($1, $2, $3, $4, $5, $6, $7)";

    private final Logger log = LoggerFactory.getLogger(AirqMeasurementRepositoryPostgres.class);
    private final Boolean schemaCreate;
    private final io.vertx.axle.pgclient.PgPool client;

    @Inject
    public AirqMeasurementRepositoryPostgres(@ConfigProperty(name = "airq-app.schema.create", defaultValue = "true") Boolean schemaCreate,
                                             PgPool client) {
        this.schemaCreate = schemaCreate;
        this.client = client;
    }

    @PostConstruct
    void config() {
        if (schemaCreate) {
            initdb();
        }
    }

    private void initdb() {
        client.query(SCHEMA)
              .toCompletableFuture()
              .join();
    }

    @Override
    public CompletionStage<Boolean> save(AirqMeasurement measurement) {
        return client.preparedQuery(INSERT_QUERY, prepareAirqMeasurementTuple(measurement))
                     .thenApply(result -> result.rowCount() != 0);
    }

    private Tuple prepareAirqMeasurementTuple(AirqMeasurement measurement) {
        final OffsetDateTime timestamp = measurement.getTimestamp();
        final Double humidity = measurement.getHumidity() != null ? measurement.getHumidity().getValue() : null;
        final Double temperature = measurement.getTemperature() != null ? measurement.getTemperature().getValue() : null;
        final Double pm10 = measurement.getPm10() != null ? measurement.getPm10().getValue() : null;
        final Double pm25 = measurement.getPm25() != null ? measurement.getPm25().getValue() : null;
        final String stationId = measurement.getStationId() != null ? measurement.getStationId().getId() : null;
        final String stationLocation = measurement.getLocation() != null ? measurement.getLocation().getLocation() : null;

        return Tuple.of(timestamp)
                    .addDouble(temperature)
                    .addDouble(humidity)
                    .addDouble(pm10)
                    .addDouble(pm25)
                    .addString(stationId)
                    .addString(stationLocation);
    }
}
