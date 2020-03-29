package ch.heigvd.easytoolz;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        registry.addMapping("/**");
    }
    @Bean
    public WebMvcConfigurer forwardToIndex() {
        return new WebMvcConfigurer () {
            @Override
            public void addViewControllers(ViewControllerRegistry registry)  {
                registry.addViewController("/").setViewName("forward:index.html");
            }
        };
    }


}
