package ru.otus.hw.springconfig;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.config.AppProperties;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = AppProperties.class)
public class Config {
}
