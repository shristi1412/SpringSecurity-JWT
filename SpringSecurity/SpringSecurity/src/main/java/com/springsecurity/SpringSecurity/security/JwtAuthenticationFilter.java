package com.springsecurity.SpringSecurity.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header: {}" + requestHeader);
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.error("could not get token");
            } catch (Exception e) {
                log.error("error while fetching token");
            }
        }
        else {
            logger.info("Invalid Header Value !! ");
        }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails us = this.userDetailsService.loadUserByUsername(username);
                Boolean validateToken = this.jwtHelper.validateToken(token, us);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(us, null, us.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.info("validation failed");
}
        filterChain.doFilter(request, response);
            }
    }

