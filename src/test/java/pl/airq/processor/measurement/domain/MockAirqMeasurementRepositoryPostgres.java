package pl.airq.processor.measurement.domain;

import io.quarkus.test.Mock;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import pl.airq.common.domain.measurement.AirqMeasurement;
import pl.airq.common.domain.phenotype.AirqPhenotype;

@Mock
@ApplicationScoped
public class MockAirqMeasurementRepositoryPostgres extends AirqMeasurementRepositoryPostgres {

    private Boolean result = Boolean.TRUE;

    public MockAirqMeasurementRepositoryPostgres() {
        super(null);
    }

    @Override
    public Uni<Boolean> save(AirqMeasurement data) {
        return Uni.createFrom().item(result);
    }

    @Override
    public Uni<Boolean> upsert(AirqMeasurement data) {
        return Uni.createFrom().item(result);
    }

    public void setSaveAndUpsertResult(Boolean result) {
        this.result = result;
    }
}
