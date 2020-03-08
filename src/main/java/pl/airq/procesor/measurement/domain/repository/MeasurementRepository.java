package pl.airq.procesor.measurement.domain.repository;

import java.util.concurrent.CompletionStage;

public interface MeasurementRepository<T> {

    CompletionStage<Boolean> save(T measurement);

}
