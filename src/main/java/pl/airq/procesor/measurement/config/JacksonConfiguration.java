package pl.airq.procesor.measurement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import java.time.LocalDateTime;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JacksonConfiguration implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDateTime.class, new UnixTimestampDeserializer());

        mapper.registerModule(module);
//              .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true)
//              .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
//              .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
    }
}
