package com.ifsp.projeto;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // [cite: 23] Permite exibir as imagens salvas automaticamente
        Path uploadDir = Paths.get("./imagens-upload");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/imagens-upload/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}