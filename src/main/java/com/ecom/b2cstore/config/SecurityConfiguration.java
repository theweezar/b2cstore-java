package com.ecom.b2cstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.ecom.b2cstore.service.CustomerService;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private CustomerService customerService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                /**
                 * Authorize requests: /checkout requires authentication, all other requests are
                 * permitted/allowed
                 * without authentication
                 * Form login configuration:
                 * - Custom login page at /login
                 * - Login processing URL at /login
                 * - Default success URL after login is / with alwaysUse set to true
                 * Logout configuration:
                 * - Logout URL at /logout
                 * - On logout success, redirect to /login?logout
                 * - Invalidate HTTP session and delete JSESSIONID cookie on logout
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/address", "/address/**", "/account", "/account/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                        .successHandler(new AuthenticationHandler(customerService)))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
