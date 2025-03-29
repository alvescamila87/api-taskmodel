package com.senai.taskmodel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin Resource Sharing (CORS) settings for the application.
 * This allows the frontend (React) to communicate with the backend (Spring Boot) without security restrictions.
 */
@Configuration
public class CorsConfig {

    /**
     *  Defines a {@link WebMvcConfigurer} bean to configure CORS settings globally.
     *  @return A {@link WebMvcConfigurer} instance with CORS settings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * Configures CORS settings for all endpoints.
             *
             * @param registry The {@link CorsRegistry} instance used to define CORS rules.
             *
             *  addMapping: allows all resources URLS paths
             *  allowOrigins: allows requests from frontend to access backend
             *  allowMethods: which methods are allowed, defines the allowed HTTP methods
             *  allowHeaders: allows all headers in requests
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
