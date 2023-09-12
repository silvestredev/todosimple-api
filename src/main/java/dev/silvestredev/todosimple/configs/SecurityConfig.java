package dev.silvestredev.todosimple.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//configuração principal de autentição
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //filtro
    @Bean //injeção de dependencia - cria um objeto gerenciado pelo spring
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http
            .authorizeHttpRequests(auth -> auth 
                .requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()
                .anyRequest().authenticated() //permitir todos da lista e autenticar qualquer outro
            );

        return http.build();
    }

    //cors config
    @Bean //injeção de dependencia - cria um objeto gerenciado pelo spring
    CorsConfigurationSource corsConfigurationSource() {
        
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE")); //liberar esses métodos

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    //criptografar a senha para o db e descriptografar quando necessário
    @Bean //injeção de dependencia - cria um objeto gerenciado pelo spring
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
