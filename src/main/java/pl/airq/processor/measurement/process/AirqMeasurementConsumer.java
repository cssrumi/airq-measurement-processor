package pl.airq.processor.measurement.process;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.domain.PersistentRepository;
import pl.airq.common.domain.measurement.AirqMeasurement;
import pl.airq.common.domain.measurement.AirqMeasurementEvent;
import pl.airq.processor.measurement.domain.AirqMeasurementFactory;

@ApplicationScoped
public class AirqMeasurementConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirqMeasurementConsumer.class);

    private final PersistentRepository<AirqMeasurement> repository;
    private final AirqMeasurementFactory factory;

    @Inject
    public AirqMeasurementConsumer(PersistentRepository<AirqMeasurement> repository, AirqMeasurementFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @ConsumeEvent("AirqMeasurementEvent")
    public Uni<Void> consume(AirqMeasurementEvent event) {
        LOGGER.info("Event arrived: {}", event);
        final AirqMeasurement measurement = factory.from(event);
        return repository.save(measurement)
                         .onItem()
                         .transform(result -> {
                             if (!result) {
                                 LOGGER.warn("AirqMeasurementEvent processing failed.");
                             }

                             return null;
                         });

    }
}
