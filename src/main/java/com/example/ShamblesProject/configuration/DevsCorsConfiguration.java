package com.example.ShamblesProject.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
@Profile("development")
public class DevsCorsConfiguration {
  public void addCorsMappings(CorsRegistry registry) {
  registry.addMapping("/**")
          .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
          .exposedHeaders("Authorization");
}
}