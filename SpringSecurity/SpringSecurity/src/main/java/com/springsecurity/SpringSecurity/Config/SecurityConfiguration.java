package com.springsecurity.SpringSecurity.Config;

import com.springsecurity.SpringSecurity.security.JwtAuthenticationEntryPoint;
import com.springsecurity.SpringSecurity.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final JwtAuthenticationEntryPoint point;

    private final JwtAuthenticationFilter filter;

    public SecurityConfiguration(JwtAuthenticationFilter filter, JwtAuthenticationEntryPoint point){
        this.point = point;
        this.filter=filter;
    }

    @Bean
    public SecurityFilterChain SecurityFilter(HttpSecurity http) throws Exception {

        http.csrf(csrf->csrf.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/admin/**")
                        .authenticated()
                        .requestMatchers("/auth/login").permitAll().anyRequest().authenticated())
                .exceptionHandling(ex->ex.authenticationEntryPoint(point))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }
}
