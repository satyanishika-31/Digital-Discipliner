package com.deploy.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Added this to ensure the config is picked up
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF so your POST forms (Login/Register) work
            .csrf(csrf -> csrf.disable()) 
            
            // 2. Allow all requests so your custom logic takes over
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login.html", "/habit.html", "/api/**", "/LoginServlet").permitAll()
                .anyRequest().permitAll()
            )
            
            // 3. Fix for H2 Console and iframe issues
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) 
            
            // 4. Explicitly disable the default Spring login/logout
            .formLogin(login -> login.disable())
            .httpBasic(basic -> basic.disable())
            .logout(logout -> logout.disable());
            
        return http.build();
    }
}