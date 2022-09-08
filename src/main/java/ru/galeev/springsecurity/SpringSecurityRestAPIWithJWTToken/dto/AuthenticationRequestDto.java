package ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
