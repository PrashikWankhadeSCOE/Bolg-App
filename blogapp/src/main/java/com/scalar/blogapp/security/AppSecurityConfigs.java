package com.scalar.blogapp.security;
import com.scalar.blogapp.users.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AppSecurityConfigs {

    private JWTAuthenticationFilter jwtAuthenticationFilter ;
    private JWTService jwtService;
    private UsersService usersService;

    public AppSecurityConfigs(JWTAuthenticationFilter jwtAuthenticationFilter, JWTService jwtService, UsersService usersService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService,usersService)
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);

        return http.build();

    }
}
