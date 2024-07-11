package ru.cft.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PlaceholderConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        properties.setLocations(
                new ClassPathResource("messages/swaggermessage.properties"),
                new ClassPathResource("messages/servicemessage.properties"),
                new ClassPathResource("messages/validationmessage.properties")
        );
        properties.setOrder(Integer.MIN_VALUE);
        return properties;
    }
}
