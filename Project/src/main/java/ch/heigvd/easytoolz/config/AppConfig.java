package ch.heigvd.easytoolz.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "ch.heigvd.easytoolz.tasks")
public class AppConfig {
}
