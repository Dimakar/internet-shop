package ru.dimakar.internetshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dimakar.internetshop.dto.SignUpRequest;
import ru.dimakar.internetshop.dto.UserDto;
import ru.dimakar.internetshop.service.UserService;

import javax.validation.Valid;

@RestController
@Validated
public class AuthSignupController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/signup")
    public UserDto signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userService.createUser(signUpRequest);
    }
}
