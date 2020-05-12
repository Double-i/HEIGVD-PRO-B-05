package ch.heigvd.easytoolz;

import ch.heigvd.easytoolz.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(
        {
                FileStorageProperties.class
        }
)
public class EasyToolzApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyToolzApplication.class, args);
    }

}

