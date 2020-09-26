package pl.airq.processor.measurement.process;

import com.hivemq.testcontainer.core.HiveMQTestContainerCore;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttResource implements QuarkusTestResourceLifecycleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttResource.class);
    private final HiveMQTestContainerCore<?> mqtt = new HiveMQTestContainerCore<>();

    @Override
    public Map<String, String> start() {
        mqtt.start();
        final Map<String, String> configMap = Map.of(
                "mp.messaging.incoming.airq-events.host", mqtt.getHost(),
                "mp.messaging.incoming.airq-events.port", Integer.toString(mqtt.getMqttPort()));
        LOGGER.info("{} class configuration: {}", MqttResource.class.getSimpleName(), configMap);
        return configMap;
    }

    @Override
    public void stop() {
        mqtt.close();
    }
}
