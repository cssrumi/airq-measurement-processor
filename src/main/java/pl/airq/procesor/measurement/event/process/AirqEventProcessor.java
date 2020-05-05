package pl.airq.procesor.measurement.event.process;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class AirqEventProcessor {

    private final Logger log = LoggerFactory.getLogger(AirqEventProcessor.class);
    private final AirqEventEmitter emitter;

    @Inject
    public AirqEventProcessor(AirqEventEmitter emitter) {
        this.emitter = emitter;
    }

    @Incoming("airq-events")
    public void consume(byte[] rawEvent) {
        String event = new String(rawEvent);
        log.debug(String.format("Event arrived: %s", event));
        try {
            emitter.emit(event);
        } catch (Exception e) {
            log.error("Unable to process event from airq-events queue.", e);
        }
    }
}
