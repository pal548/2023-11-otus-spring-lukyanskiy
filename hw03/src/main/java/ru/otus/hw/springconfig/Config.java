package ru.otus.hw.springconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.config.AppProperties;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class Config {
}
