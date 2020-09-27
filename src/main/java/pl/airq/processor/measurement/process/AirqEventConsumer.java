package pl.airq.processor.measurement.process;

import io.smallrye.mutiny.Uni;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.domain.exception.ProcessingException;
import pl.airq.common.process.AppEventBus;
import pl.airq.common.process.EventParser;

@ApplicationScoped
public class AirqEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirqEventConsumer.class);

    private final EventParser parser;
    private final AppEventBus bus;


    @Inject
    public AirqEventConsumer(EventParser parser, AppEventBus bus) {
        this.parser = parser;
        this.bus = bus;
    }

    @Incoming("airq-events")
    public void consume(byte[] rawEvent) {
        Uni.createFrom().item(rawEvent)
           .onItem().ifNull().failWith(new ProcessingException("Empty event occurred."))
           .map(String::new)
           .invoke(event -> LOGGER.info("Processing: {}", event))
           .map(parser::deserializeDomainEvent)
           .invoke(bus::publish)
           .onFailure()
           .invoke(e -> LOGGER.error("Unable to process event from airq-events queue.", e))
           .await()
           .atMost(Duration.ofSeconds(5));
    }
}
