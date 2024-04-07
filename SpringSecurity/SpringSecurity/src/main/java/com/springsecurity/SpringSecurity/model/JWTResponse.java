package com.springsecurity.SpringSecurity.model;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponse {
    private String token;
    private String username;
}
