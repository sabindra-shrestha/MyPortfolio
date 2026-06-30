package com.sabindra.portfolio.config;


import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    //Bean - Spring calls once, and then hands the result to anyone who asks for a PasswordEncoder.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();//BCrypt is one-way hashing algorithm - you can turn a password into a hash,
        //but you can never turn the hash back into the password.
    }

//    //Check if this username+password combo is correct
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
//        return config.getAuthenticationManager();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                //CSRF protection is a defense designed for browser session cookies.
                //we're not using cookies--we're using JWT's in headers -- so this specific protection doesn't apply
                //we disable it -- we turn it off.
                .csrf(csrf -> csrf.disable())
                //plug in our CORS rules -- this is what lets your Angular app, running in different port , actually talk to this APT at all.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //JWT auth-- never create a server-side session for anyone, ever. every request must prove who it is on its own.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //Read top to bottom, first match wins--permitAll()=no token needed.
                //anyRequest().authenticated(),, is the catch-all(anything not explicitly listed above requires a valid, authenticated request.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/projects/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/blogs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/skills/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/contact").permitAll()
                        .anyRequest().authenticated()
                        //Wires jwtAuthFilter into spring internal pipeline, telling it to run before Spring's own built-in login filter.
                ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //Angular app will run on localhost:4200, Spring Boot on localhost:8080 -- different ports count as "different origins"

    //This config explicitly tells the browser -- request from port 4200, using these http methods, with any headers, are allowed.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}
