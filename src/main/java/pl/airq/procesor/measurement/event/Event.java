package pl.airq.procesor.measurement.event;

import pl.airq.procesor.measurement.event.payload.Payload;

public interface Event {

    Payload getPayload();

}
