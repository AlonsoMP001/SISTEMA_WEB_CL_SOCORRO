package com.clinicasocorro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/staff", "/css/**", "/js/**", "/login").permitAll() // Rutas públicas
                .anyRequest().authenticated() // Todo lo demás requiere login
            )
            .formLogin(form -> form
                .loginPage("/login") // Nuestra página personalizada
                .defaultSuccessUrl("/login-success", true) // A donde irá tras éxito
                .permitAll()	
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Deshabilitamos CSRF temporalmente para facilitar las pruebas de login

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Seguimos usando esto porque mis claves en la BD son texto plano ('123') para prueba, luego sera cifrado de contraseña
        return NoOpPasswordEncoder.getInstance();
    }
}