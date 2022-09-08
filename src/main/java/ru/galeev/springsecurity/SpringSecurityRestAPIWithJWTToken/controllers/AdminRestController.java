package ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.dto.AdminUserDto;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.models.User;
import ru.galeev.springsecurity.SpringSecurityRestAPIWithJWTToken.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/admin")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/allUsers")
    public ResponseEntity<List<AdminUserDto>> getAll() {
        List<AdminUserDto> result = userService.getAll().stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
