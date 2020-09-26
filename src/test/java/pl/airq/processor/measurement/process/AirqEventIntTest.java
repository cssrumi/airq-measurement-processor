package pl.airq.processor.measurement.process;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import java.time.Duration;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.airq.common.domain.measurement.AirqMeasurement;
import pl.airq.common.domain.measurement.AirqMeasurementEvent;
import pl.airq.processor.measurement.domain.MockAirqMeasurementRepositoryPostgres;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.wildfly.common.Assert.assertNotNull;

@QuarkusTest
@QuarkusTestResource(MqttResource.class)
class AirqEventIntTest {

    private static final String RAW_AIRQ_MEASUREMENT_EVENT = "{\"eventType\": \"AirqMeasurement\",\"timestamp\": 1599854402,\"payload\": " +
            "{\"Temperature\": \"23.70°C\"," +
            "\"Humidity\": \"66%\"," +
            "\"PM10\": 518," +
            "\"PM25\": 376," +
            "\"stationId\": \"00000000164ab68a\"," +
            "\"Location\": \"5424.831306,N,01826.459895,E\"}}";

    @ConfigProperty(name = "mp.messaging.incoming.airq-events.topic")
    private String topic;
    @InjectSpy
    private MockAirqMeasurementRepositoryPostgres repository;
    @InjectSpy
    private AirqEventConsumer airqEventConsumer;
    @InjectSpy
    private AirqMeasurementConsumer airqMeasurementConsumer;
    @Inject
    private Mqtt5BlockingClient mqttClient;

    @BeforeEach
    void beforeEach() {
        repository.setSaveAndUpsertResult(Boolean.TRUE);
    }

    @Test
    void emit_withAirqMeasurementEvent_expectDeserializedEventSendAndSaved() {
        ArgumentCaptor<AirqMeasurement> measurementCaptor = ArgumentCaptor.forClass(AirqMeasurement.class);

        sendMqttMessage(RAW_AIRQ_MEASUREMENT_EVENT);

        verify(airqEventConsumer, timeout(Duration.ofSeconds(2).toMillis())).consume(any(byte[].class));
        verify(airqMeasurementConsumer, timeout(Duration.ofSeconds(10).toMillis())).consume(any(AirqMeasurementEvent.class));
        verify(repository, timeout(Duration.ofSeconds(10).toMillis())).save(measurementCaptor.capture());
        assertNotNull(measurementCaptor.getValue());
        assertNotNull(measurementCaptor.getValue().humidity);
        assertNotNull(measurementCaptor.getValue().temperature);
        assertNotNull(measurementCaptor.getValue().timestamp);
        assertNotNull(measurementCaptor.getValue().pm10);
        assertNotNull(measurementCaptor.getValue().pm25);
        assertNotNull(measurementCaptor.getValue().stationId);
        assertNotNull(measurementCaptor.getValue().location);
    }

    private void sendMqttMessage(String payload) {
        mqttClient.connect();
        mqttClient.publishWith().topic(topic).qos(MqttQos.EXACTLY_ONCE).payload(payload.getBytes()).send();
        mqttClient.disconnect();
    }

    @Dependent
    public static class MqttClientConfiguration {

        @Produces
        public Mqtt5BlockingClient client(@ConfigProperty(name = "mp.messaging.incoming.airq-events.host") String host,
                                          @ConfigProperty(name = "mp.messaging.incoming.airq-events.port") String port) {
            return Mqtt5Client.builder().serverHost(host).serverPort(Integer.parseInt(port)).buildBlocking();
        }
    }

}
