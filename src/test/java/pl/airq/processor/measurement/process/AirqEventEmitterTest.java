package pl.airq.processor.measurement.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.airq.common.domain.event.AirqMeasurementEvent;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@QuarkusTest
class AirqEventEmitterTest {

    private static final String RAW_AIRQ_MEASUREMENT_EVENT = "{\"eventType\": \"AirqMeasurement\",\"timestamp\": 1599854402,\"payload\": " +
            "{\"Temperature\": \"23.70°C\",\"Humidity\": \"66%\",\"PM10\": 518,\"PM25\": 376,\"stationId\": \"00000000164ab68a\",\"Location\": \"5424.831306,N,01826.459895,E\"}}";

    private EventBus eventBus;

    @Inject
    private ObjectMapper objectMapper;

    private AirqEventEmitter airqEventEmitter;

    @BeforeEach
    void beforeEach() {
        eventBus = mock(EventBus.class);
        airqEventEmitter = new AirqEventEmitter(objectMapper, eventBus);
    }

    @Test
    void emit_withAirqMeasurementEvent_expectDeserializedEventSend() {
        airqEventEmitter.emit(RAW_AIRQ_MEASUREMENT_EVENT);

        ArgumentCaptor<AirqMeasurementEvent> eventCaptor = ArgumentCaptor.forClass(AirqMeasurementEvent.class);
        verify(eventBus).sendAndForget(anyString(), eventCaptor.capture());
        assertNotNull(eventCaptor.getValue());
        assertNotNull(eventCaptor.getValue().payload);
    }

}
