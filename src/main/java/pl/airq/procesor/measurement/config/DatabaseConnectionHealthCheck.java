package pl.airq.procesor.measurement.config;

import java.util.concurrent.ExecutionException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import pl.airq.procesor.measurement.domain.repository.AirqMeasurementRepository;

@Readiness
@ApplicationScoped
public class DatabaseConnectionHealthCheck implements HealthCheck {

    private static final String HEALTH_CHECK = "Database";
    private final AirqMeasurementRepository airqMeasurementRepository;

    @Inject
    public DatabaseConnectionHealthCheck(AirqMeasurementRepository airqMeasurementRepository) {
        this.airqMeasurementRepository = airqMeasurementRepository;
    }

    @Override
    public HealthCheckResponse call() {
        try {
            return airqMeasurementRepository.healthCheck()
                                            .thenApply(bool -> bool == Boolean.TRUE
                                                    ? HealthCheckResponse.up(HEALTH_CHECK)
                                                    : HealthCheckResponse.down(HEALTH_CHECK))
                                            .toCompletableFuture()
                                            .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return HealthCheckResponse.down(HEALTH_CHECK);
        }
    }
}
