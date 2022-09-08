package ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.dto.AuthenticationRequestDto;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.dto.UserDto;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.models.User;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.secutiry.jwt.JwtTokenProvider;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.secutiry.jwt.UserNotFoundException;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));

            String token = jwtTokenProvider.createToken(username, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(Map.of("Message", "Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Map<Object, Object>> register(@RequestBody UserDto userDto) {
        User user = userDto.toUser();
        userService.register(user);

        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
