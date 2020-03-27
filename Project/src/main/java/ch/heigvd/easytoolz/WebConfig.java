package ch.heigvd.easytoolz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/*@EnableWebMvc*/ // <<-- TODO : delete me  | ici le problème
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api",
                HandlerTypePredicate.forAnnotation(RestController.class));
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
