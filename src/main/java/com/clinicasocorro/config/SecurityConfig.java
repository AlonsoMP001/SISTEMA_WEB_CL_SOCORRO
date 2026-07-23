package com.clinicasocorro.config;

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/staff", "/css/**", "/js/**", "/img/**", "/login", "/registro")
                        .permitAll() // Rutas públicas
                        .requestMatchers("/medico/**").hasAuthority("ROLE_MEDICO")
                        .anyRequest().authenticated() // Todo lo demás requiere login
                )
                .formLogin(form -> form
                        .loginPage("/login") // Nuestra página personalizada
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            String redirectUrl = "/";
                            for (var authority : authentication.getAuthorities()) {
                                if (authority.getAuthority().equals("ROLE_MEDICO")) {
                                    redirectUrl = "/medico/perfil";
                                    break;
                                } else if (authority.getAuthority().equals("ROLE_ADMINISTRADOR")) {
                                    redirectUrl = "/admin/dashboard"; // Placeholder para el futuro
                                    break;
                                }
                            }
                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
