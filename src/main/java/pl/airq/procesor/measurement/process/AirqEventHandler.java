package pl.airq.procesor.measurement.process;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.domain.PersistentRepository;
import pl.airq.common.domain.event.AirqMeasurementEvent;
import pl.airq.common.domain.measurement.AirqMeasurement;
import pl.airq.procesor.measurement.domain.AirqMeasurementFactory;

@ApplicationScoped
public class AirqEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(AirqEventHandler.class);
    private final PersistentRepository<AirqMeasurement> repository;
    private final AirqMeasurementFactory factory;

    @Inject
    public AirqEventHandler(PersistentRepository<AirqMeasurement> repository, AirqMeasurementFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @ConsumeEvent("AirqMeasurementEvent")
    Uni<Void> consume(AirqMeasurementEvent event) {
        final AirqMeasurement measurement = factory.from(event);
        return repository.save(measurement).onItem().apply(result -> {
            if (!result) {
                LOGGER.warn("AirqMeasurementEvent processing failed.");
            }

            return null;
        });
    }
}
