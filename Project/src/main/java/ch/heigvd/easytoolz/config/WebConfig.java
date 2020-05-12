package ch.heigvd.easytoolz.config;

import ch.heigvd.easytoolz.tasks.MailTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api",
                HandlerTypePredicate.forAnnotation(RestController.class));
    }

    // TODO : voir si à supprimer, permet de pouvoir faire des requêtes cross origin pour utiliser le frontend avec  npm start
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://127.0.0.1:3000","http://localhost:3000").allowedMethods("POST", "PATCH", "GET", "DELETE", "PUT").allowCredentials(true);
    }

    // redirect all the url to index.html (frontend entry point)
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{spring:\\w+}")
                .setViewName("forward:/index.html");
        registry.addViewController("/**/{spring:\\w+}")
                .setViewName("forward:/index.html");
        registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
                .setViewName("forward:/index.html");
    }


}