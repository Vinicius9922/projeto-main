package com.ifsp.projeto.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("./imagens-upload");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/imagens-upload/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}