package eu.sia.meda;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.config.ArchConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/** Empty Spring context to load for simple spring unit test  */
@ConditionalOnMissingBean(ArchConfiguration.class)
@SpringBootConfiguration
public class DummyConfiguration{
    @Bean
    @Primary
    public ObjectMapper buildObjectMapper(){
        return new ArchConfiguration().objectMapper();
    }
    @Bean
    @Qualifier("StrictObjectMapper")
    public ObjectMapper buildObjectMapperStrict(){
        return new ArchConfiguration().objectMapperStrict();
    }
}
