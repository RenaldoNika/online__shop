package com.example.shopping.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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

    private PersonDetails personDetails;
    private UserAuthenticationSuccesHandler userAuthenticationSuccesHandler;

    @Autowired
    public SecurityConfig(PersonDetails personDetails,
                          UserAuthenticationSuccesHandler userAuthenticationSuccesHandler) {
        this.personDetails = personDetails;
        this.userAuthenticationSuccesHandler = userAuthenticationSuccesHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/shop/online").permitAll()
                        .requestMatchers("/products/category").permitAll()
                        .requestMatchers("/products/filterPrice").permitAll()
                        .requestMatchers("/products/filterByName").permitAll()
                        .requestMatchers("/user/signup").permitAll()
                        .requestMatchers("/user/register").permitAll()
                        .requestMatchers("/order/**").hasRole("user")
                        .requestMatchers("/admin/home").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(form->
                        form.successHandler(userAuthenticationSuccesHandler)
                                .permitAll()
                )

                .logout(out -> out
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .userDetailsService(personDetails);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
