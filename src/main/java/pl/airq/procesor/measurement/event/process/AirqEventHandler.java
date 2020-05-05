package pl.airq.procesor.measurement.event.process;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.procesor.measurement.domain.AirqMeasurement;
import pl.airq.procesor.measurement.domain.AirqMeasurementFactory;
import pl.airq.procesor.measurement.domain.repository.PersistentRepository;
import pl.airq.procesor.measurement.event.AirqMeasurementEvent;

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
