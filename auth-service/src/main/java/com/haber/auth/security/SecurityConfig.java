package com.haber.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // /api/auth/** endpoint'lerine herkes erişebilir
                        .requestMatchers("/api/public/**").permitAll() // Herkese açık endpoint'ler
                        .requestMatchers("/api/member/**").hasAnyRole("MEMBER", "EDITOR", "ADMIN") // Üye, Editör ve Admin erişebilir
                        .requestMatchers("/api/editor/**").hasAnyRole("EDITOR", "ADMIN") // Editör ve Admin erişebilir
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Sadece Admin erişebilir
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Şifreleme için BCrypt kullan
    }
}