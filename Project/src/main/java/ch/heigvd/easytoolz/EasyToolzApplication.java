package ch.heigvd.easytoolz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

