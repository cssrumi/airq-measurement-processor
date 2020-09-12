package pl.airq.processor.measurement.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.jackson.ObjectMapperCustomizer;
import javax.inject.Singleton;

@Deprecated
@Singleton
public class JacksonConfiguration implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }
}
