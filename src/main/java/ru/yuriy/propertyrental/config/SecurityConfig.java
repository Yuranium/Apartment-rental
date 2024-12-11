package ru.yuriy.propertyrental.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig
{
    private final UserDetailsService service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception
    {
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/static/**", "/", "/apartments/all", "/apartments/search",
                                "/apartments/sort", "/apartments/api/autocomplete", "/registration",
                                "/login", "/confirm", "/image/**", "/apartments/**", "/api/**")
                        .permitAll()
                        .requestMatchers("/apartments/add", "/profile/**", "/deleteProfile/**", "/payment/**")
                        .authenticated()
                        .requestMatchers("/admin/**", "/graphiql/**", "/graphql/**")
                        .hasRole("ADMIN"))
                .userDetailsService(service)
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/login?error")
                        .permitAll()
                        .usernameParameter("email")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
}