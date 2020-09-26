package pl.airq.processor.measurement.domain;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import java.time.OffsetDateTime;
import javax.inject.Inject;
import javax.inject.Singleton;
import pl.airq.common.domain.measurement.AirqMeasurement;
import pl.airq.common.infrastructure.persistance.PersistentRepositoryPostgres;

@Singleton
public class AirqMeasurementRepositoryPostgres extends PersistentRepositoryPostgres<AirqMeasurement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirqMeasurementRepositoryPostgres.class);
    private static final String INSERT_QUERY = "INSERT INTO" +
            " AIRQ_MEASUREMENT (timestamp, temperature, humidity, pm10, pm25, stationId, location)" +
            " VALUES ($1, $2, $3, $4, $5, $6, $7)";

    @Inject
    public AirqMeasurementRepositoryPostgres(PgPool client) {
        super(client);
    }

    @Override
    protected String insertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected Tuple prepareTuple(AirqMeasurement measurement) {
        final OffsetDateTime timestamp = measurement.timestamp;
        final Double humidity = measurement.humidity != null ? measurement.humidity.getDoubleValue() : null;
        final Double temperature = measurement.temperature != null ? measurement.temperature.getDoubleValue() : null;
        final Double pm10 = measurement.pm10 != null ? measurement.pm10.getDoubleValue() : null;
        final Double pm25 = measurement.pm25 != null ? measurement.pm25.getDoubleValue() : null;
        final String stationId = measurement.stationId != null ? measurement.stationId.getId() : null;
        final String stationLocation = measurement.location != null ? measurement.location.getLocation() : null;

        return Tuple.of(timestamp)
                    .addDouble(temperature)
                    .addDouble(humidity)
                    .addDouble(pm10)
                    .addDouble(pm25)
                    .addString(stationId)
                    .addString(stationLocation);
    }

    @Override
    protected void postSaveAction(RowSet<Row> saveResult) {
    }

    @Override
    protected void postProcessAction(Boolean result, AirqMeasurement data) {
        if (Boolean.TRUE.equals(result)) {
            LOGGER.info("AirqMeasurement saved successfully.");
            return;
        }
        LOGGER.warn("Unable to save AirqMeasurement: " + data);
    }
}
