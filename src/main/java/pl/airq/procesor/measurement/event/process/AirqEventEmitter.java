package pl.airq.procesor.measurement.event.process;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AirqEventEmitter {

    private final Logger log = LoggerFactory.getLogger(AirqEventEmitter.class);
    private final ObjectMapper mapper;
    private final EventBus bus;

    @Inject
    public AirqEventEmitter(ObjectMapper mapper, EventBus bus) {
        this.mapper = mapper;
        this.bus = bus;
    }

    void emit(String event) {
        try {
            JsonNode jsonNode = mapper.readTree(event);
            final String eventType = jsonNode.get("eventType").asText();
            final Class<?> clsType = Class.forName(String.format("pl.airq.procesor.measurement.event.%sEvent", eventType));
            final Object object = mapper.treeToValue(jsonNode, clsType);
            final String type = clsType.getSimpleName();
            bus.sendAndForget(type, object);
            log.info(String.format("Parsed event: %s\nSend to: %s", object.toString(), type));
        } catch (Exception ex) {
            log.error("Unable to parse class", ex);
        }
    }
}
