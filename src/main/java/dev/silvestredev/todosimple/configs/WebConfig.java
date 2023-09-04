package dev.silvestredev.todosimple.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    //configuração para liberar acesso a tudo da API
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
