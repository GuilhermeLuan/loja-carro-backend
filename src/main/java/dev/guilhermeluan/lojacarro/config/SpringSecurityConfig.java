package dev.guilhermeluan.lojacarro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    private static final String[] WHITE_LIST = {"/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/csrf"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/vehicles/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/vehicles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/vehicles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/v1/vehicles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/vehicles/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
