package com.example.assinatura.rsa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(cf -> cf.disable())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS.STATELESS)
                ).authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.GET, "/rsa").permitAll()
                                .requestMatchers(HttpMethod.POST, "/tarefa").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tarefa").permitAll()
                );
        return http.build();
    }
}
