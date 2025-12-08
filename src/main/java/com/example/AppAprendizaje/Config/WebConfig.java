package com.example.AppAprendizaje.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapea la URL /uploads/** a la carpeta en disco C:/Users/USER/uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/Users/USER/uploads/");
    }
}
