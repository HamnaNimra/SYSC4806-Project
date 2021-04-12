package Sysc4806Group.demo.config;

import org.ff4j.FF4j;
import org.ff4j.audit.repository.InMemoryEventRepository;
import org.ff4j.property.store.InMemoryPropertyStore;
import org.ff4j.spring.boot.web.api.config.EnableFF4jSwagger;
import org.ff4j.store.InMemoryFeatureStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFF4jSwagger
public class FF4jConfig {

    @Bean
    public FF4j getFF4j() {
        return new FF4j("ff4j-features.xml");
    }
}
