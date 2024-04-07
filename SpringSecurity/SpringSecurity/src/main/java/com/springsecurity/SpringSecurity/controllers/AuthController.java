package com.springsecurity.SpringSecurity.controllers;

import com.springsecurity.SpringSecurity.model.JWTRequest;
import com.springsecurity.SpringSecurity.model.JWTResponse;
import com.springsecurity.SpringSecurity.security.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest jwtrequest){
    this.doAuthenticate( (Objects.requireNonNull(jwtrequest.getEmail())),(Objects.requireNonNull(jwtrequest.getPassword())) );
        UserDetails user = userDetailsService.loadUserByUsername(jwtrequest.getEmail());
        String token = this.jwtHelper.generateToken(user);

        JWTResponse response = JWTResponse.builder().token(token)
                .username(user.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    public void doAuthenticate(String email, String password){
        UsernamePasswordAuthenticationToken use = new UsernamePasswordAuthenticationToken(email,password);
        try {
            authenticationManager.authenticate(use);
        }catch(BadCredentialsException e){
            log.error("could not authenticate");
        }

    }
}
