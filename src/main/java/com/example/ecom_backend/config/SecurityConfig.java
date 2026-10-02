package com.example.ecom_backend.config;

import com.example.ecom_backend.security.CustomerUserDetailsService;
import com.example.ecom_backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception{

        http
                .cors(Customizer.withDefaults())
                //disable CSRF(for REST APIs)
                .csrf(csrf->csrf.disable())

                //no HTTP Session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //URL Authorization
                .authorizeHttpRequests(auth -> auth

                        //public authentication APIs
                        .requestMatchers(
                                HttpMethod.POST,"/api/auth/**"
                        ).permitAll()

                        //Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        //public APIs
                        .requestMatchers(HttpMethod.GET,
                                "/api/categories/**",
                                "/api/subcategories/**",
                                "/api/products/**",
                                "/api/products/best-sellers",
                                "/api/reviews/product/**"
                        ).permitAll()

                        //Authenticated users
                        .requestMatchers(
                                "/api/cart/**",
                                "/api/wishlist/**",
                                "/api/addresses/**"
                        ).hasRole("USER")

                        .requestMatchers(
                                "/api/users/me",
                                "/api/reviews/**",
                                "/api/orders/**"
                        ).hasAnyRole("USER", "ADMIN")

                        //Admin APIs
                        .requestMatchers(HttpMethod.POST,
                                "/api/categories",
                                "/api/subcategories",
                                "/api/products"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/categories/**",
                                "/api/subcategories/**",
                                "/api/products/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/categories/**",
                                "/api/subcategories/**",
                                "/api/products/**",
                                "/api/reviews/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                "/api/admin/**",
                                "/api/orders/admin/**"
                        ).hasRole("ADMIN")

                        //everything else
                        .anyRequest()
                        .authenticated()

                )
                //add jwt filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authenticationProvider(authenticationProvider);


        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomerUserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
