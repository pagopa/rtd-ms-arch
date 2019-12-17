package eu.sia.meda;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.config.ArchConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/** Empty Spring context to load for simple spring unit test  */
@ConditionalOnMissingBean(ArchConfiguration.class)
@SpringBootConfiguration
public class DummyConfiguration{
    @Bean
    public ObjectMapper buildObjectMapper(){
        return new ArchConfiguration().objectMapper();
    }
}
