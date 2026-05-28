package com.store.api.controller;

import com.store.api.dto.Login;
import com.store.api.dto.Register;
import com.store.api.service.AuthorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody Register request) {
        return authorizationService.register(request);
    }

    @GetMapping("/login")
    public String login(@RequestBody Login login) {
        return authorizationService.login(login);
    }
}
