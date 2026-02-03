package com.devsenai2a.AvalliarAluno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class WebConfig {
        @Bean
        WebMvcConfigurer corsConfigurer() {
            return(WebMvcConfigurer) new WebMvcConfigurer() {

                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedMethods("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE");
                }
            };
        }
    }
}
