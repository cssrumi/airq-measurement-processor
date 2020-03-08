package pl.airq.procesor.measurement.process;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.procesor.measurement.domain.AirqMeasurement;
import pl.airq.procesor.measurement.domain.AirqMeasurementFactory;
import pl.airq.procesor.measurement.domain.repository.AirqMeasurementRepository;
import pl.airq.procesor.measurement.event.AirqMeasurementEvent;

@ApplicationScoped
public class AirqEventHandler {

    private final Logger log = LoggerFactory.getLogger(AirqEventHandler.class);
    private final AirqMeasurementRepository repository;
    private final AirqMeasurementFactory factory;

    @Inject
    public AirqEventHandler(AirqMeasurementRepository repository, AirqMeasurementFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @ConsumeEvent("AirqMeasurementEvent")
    CompletionStage<Void> consume(AirqMeasurementEvent event) {
        final AirqMeasurement measurement = factory.from(event);
        return repository.save(measurement)
                         .thenAccept(result -> {
                             if (result) {
                                 log.debug("AirqMeasurement saved successfully.\nMessage: " + measurement.toString());
                             } else {
                                 log.warn("Unable to save AirqMeasurement");
                             }
                         });
    }
}
