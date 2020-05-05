package pl.airq.procesor.measurement.domain.repository;

import io.smallrye.mutiny.Uni;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.procesor.measurement.domain.AirqMeasurement;

@ApplicationScoped
public class AirqMeasurementRepositoryPostgres implements PersistentRepository<AirqMeasurement> {

    final String INSERT_QUERY = "INSERT INTO AIRQ_MEASUREMENT (timestamp, temperature, humidity, pm10, pm25, stationId, location) VALUES ($1, $2, $3, $4, $5, $6, $7)";
    private final Logger LOGGER = LoggerFactory.getLogger(AirqMeasurementRepositoryPostgres.class);
    private final PgPool client;

    @Inject
    public AirqMeasurementRepositoryPostgres(PgPool client) {
        this.client = client;
    }


    @Override
    public Uni<Boolean> save(AirqMeasurement measurement) {
        return client.preparedQuery(INSERT_QUERY, prepareAirqMeasurementTuple(measurement))
                     .onItem()
                     .apply(result -> {
                         if (result.rowCount() != 0) {
                             LOGGER.debug("AirqMeasurement saved successfully.");
                             return true;
                         }

                         LOGGER.warn("Unable to save AirqMeasurement: " + measurement);
                         return false;
                     });
    }

    @Override
    public Uni<Boolean> upsert(AirqMeasurement measurement) {
        return save(measurement);
    }

    private Tuple prepareAirqMeasurementTuple(AirqMeasurement measurement) {
        final OffsetDateTime timestamp = measurement.timestamp;
        final Double humidity = measurement.humidity != null ? measurement.humidity.getValue() : null;
        final Double temperature = measurement.temperature != null ? measurement.temperature.getValue() : null;
        final Double pm10 = measurement.pm10 != null ? measurement.pm10.getValue() : null;
        final Double pm25 = measurement.pm25 != null ? measurement.pm25.getValue() : null;
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
}
