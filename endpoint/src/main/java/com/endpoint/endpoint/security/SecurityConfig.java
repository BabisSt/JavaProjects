/**
 * SecurityConfig sets up basic security for the Book API.
 *
 * - Defines two in-memory users: "user" with role USER, and "admin" with role ADMIN.
 * - Secures endpoints under "/books/**" to require authentication.
 * - Enables method-level security with @PreAuthorize annotations.
 * - Uses HTTP Basic authentication for simplicity.
 * - Disables CSRF for testing purposes (not recommended for production).
 */

package com.endpoint.endpoint.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // enables @PreAuthorize for method level security
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password") // {noop} means no encoding, plain text password
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}adminpass")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity, enable later in production
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/books/**").authenticated() // all book endpoints require auth
                        .anyRequest().permitAll() // other endpoints are open
                )
                .httpBasic(); // use HTTP Basic Auth for simplicity (username/password in header)

        return http.build();
    }
}
