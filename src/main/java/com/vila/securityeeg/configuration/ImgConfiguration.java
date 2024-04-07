package com.vila.securityeeg.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImgConfiguration implements WebMvcConfigurer{
     @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/imagenes/**").addResourceLocations("file:/D:/libreria/");

    }
}
