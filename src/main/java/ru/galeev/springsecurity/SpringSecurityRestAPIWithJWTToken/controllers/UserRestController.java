package ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.secutiry.jwt.JwtUser;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/users")
public class UserRestController {

    @GetMapping(value = "/showUserInfo")
    public ResponseEntity<Map<Object, Object>> showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) authentication.getPrincipal();
        Map<Object, Object> result = new LinkedHashMap<>();
        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("firstName", user.getFirstname());
        result.put("lastName", user.getLastname());
        result.put("email", user.getEmail());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
