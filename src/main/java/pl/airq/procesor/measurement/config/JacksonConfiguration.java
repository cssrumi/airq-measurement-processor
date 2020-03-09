package pl.airq.procesor.measurement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import java.time.OffsetDateTime;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JacksonConfiguration implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(OffsetDateTime.class, new UnixTimestampDeserializer());

        mapper.registerModule(module);
    }
}
