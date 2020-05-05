package pl.airq.procesor.measurement.domain.repository;

import io.smallrye.mutiny.Uni;

public interface PersistentRepository<T> {

    Uni<Boolean> save(T measurement);

    Uni<Boolean> upsert(T measurement);

}
