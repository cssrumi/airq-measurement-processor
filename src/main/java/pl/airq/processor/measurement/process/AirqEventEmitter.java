package pl.airq.processor.measurement.process;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AirqEventEmitter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirqEventEmitter.class);

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
            final Class<?> clsType = Class.forName(String.format("pl.airq.common.domain.event.%sEvent", eventType));
            final Object object = mapper.treeToValue(jsonNode, clsType);
            final String type = clsType.getSimpleName();
            bus.sendAndForget(type, object);
            LOGGER.info(String.format("Parsed event: %s\nSend to: %s handler", object.toString(), type));
        } catch (Exception ex) {
            LOGGER.error("Unable to parse class", ex);
        }
    }
}
