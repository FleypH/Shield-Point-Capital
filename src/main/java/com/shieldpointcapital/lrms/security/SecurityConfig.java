package com.shieldpointcapital.lrms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    // password encoder
    // BCrypt is the hashing algorithm for passwords
    //whens staff logs in, BCrypt hashes the provided password
    //and compares it to the stored hash - never decrypts the password

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication provider

    //connects spring Security to our CustomerUserDetailsService
    //and our BCrypt password encoder
    // spring uses this to verify login credentials

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Authentication manager

    // used by the login controller to trigger authentication
    // You inject this into AuthController and call
    // authenticationManager.authenticate(...)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // security filter chain

    // the main security rulebook
    // defines which endpoints need authentication and which do not
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //disable CRSF - not needed for REST APIs
        //CSRF is for the browser-based app and sessions
        //Our mobile app uses JWT tokens, not sessions
        http.csrf(csrf -> csrf.disable())

            // Define endpoint access rules
            .authorizeHttpRequests(auth -> auth
                //Public endpoints - no token needed
                .requestMatchers(
                    "/api/v1/auth/login",
                    "/api/v1/auth/register",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/actuator/health"
                ).permitAll()

                //Admin only endpoints
                .requestMatchers(
                    "/api/v1/staff/**",
                    "/api/v1/accounts/**",
                    "/api/v1/capital/**"
                ). hasRole("ADMIN")

                //Admin and Manager endpoints
                .requestMatchers(
                    "/api/v1/reports/**",
                    "/api/v1/ledger/**"
                ).hasAnyRole("ADMIN", "MANAGER")
                
                //All authenticated staff can acces these
                .requestMatchers(
                    "/api/v1/borrowers/**",
                    "/api/v1/loans/**",
                    "/api/v1/payments/**"
                ).hasAnyRole("ADMIN", "MANAGER", "LOAN_OFFICER", "ACCOUNTANT")

                //Everything else requires authentication
                .anyRequest().authenticated()
            )
            //stateless - no HTTP sessions
            //Every request must carry its own JWT token
            //the server rembers nothing between requests
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Register our authentication provider
            .authenticationProvider(authenticationProvider())

            //Add JWT filter BEFORE  spring's default login filter
            // so our token checks runs first on every request
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
