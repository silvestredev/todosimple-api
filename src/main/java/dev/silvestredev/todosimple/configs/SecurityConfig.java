package dev.silvestredev.todosimple.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//configuração principal de autentição
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    //rotas públicas - não precisam de autenticação
    private static final String[] PUBLIC_MATCHERS = {
        "/"
    };

    //rotas tipo POST públicas - não precisam de autenticação
    private static final String[] PUBLIC_MATCHERS_POST = {
        "/users/register",
        "/login" //abstrato do spring - o próprio spring implementa
    };

    //filtro
    @Bean //injeção de dependencia - cria um objeto gerenciado pelo spring
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http
        .csrf(csrf -> csrf.disable()) //desativando csrf
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // politica de sessao - não salvar sessão (STATELESS)
        .authorizeHttpRequests(auth -> auth 
            .requestMatchers(PUBLIC_MATCHERS_POST) //qualquer request post presente na lista public_matchers_post será permitido
            .permitAll().anyRequest().authenticated() //permitir todos da lista e autenticar qualquer outro
            .requestMatchers(PUBLIC_MATCHERS) //qualquer request presente na lista public_matchers será permitido
            .permitAll().anyRequest().authenticated() //permitir todos da lista e autenticar qualquer outro
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
