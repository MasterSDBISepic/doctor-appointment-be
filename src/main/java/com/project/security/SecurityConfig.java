package com.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoderConfig.passwordEncoder().encode("admin")).roles("ADMIN");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .formLogin(login ->
                        login
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/admin/dashboard")
                                .successHandler((request, response, authentication) -> {
                                    // Add logging for debugging
                                    System.out.println("Successful login: " + authentication.getName());
                                    response.sendRedirect("/admin/dashboard");
                                })
                                .failureHandler((request, response, exception) -> {
                                    // Add logging for debugging
                                    System.out.println("Login failed: " + exception.getMessage());
                                    response.sendRedirect("/login?error");
                                })
                );

        return http.build();
    }

}
