package org.example.gestion_product_spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Step 7: ADMIN can do all CRUD, USER is read-only.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("user").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/products", true).permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?logout"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/webjars/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        // read-only: any authenticated user
                        .requestMatchers("/", "/products").hasAnyRole("USER", "ADMIN")
                        // CRUD: admin only
                        .requestMatchers("/newProduct", "/saveProduct", "/editProduct",
                                "/updateProduct", "/deleteProduct").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.accessDeniedPage("/notAuthorized"))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(h -> h.frameOptions(f -> f.disable())); // allow H2 console iframe
        return http.build();
    }
}
