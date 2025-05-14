package com.murilogustavo.luizalabs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/swagger-ui.html",
                                "/api/swagger-ui/**",
                                "/api/v3/api-docs/**",
                                "/api/swagger-resources/**",
                                "/api/webjars/**",
                                "/api/h2-console/**"
                        ).permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api")) {
                                response.addHeader("WWW-Authenticate", "Basic realm=\"Realm\"");
                                response.sendError(401, authException.getMessage());
                            } else {
                                response.setStatus(200);
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}