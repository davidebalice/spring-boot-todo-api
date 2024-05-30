package com.todoapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapi.security.AuthRequest;
import com.todoapi.security.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "REST APIs for Login",
        description = "User Login and Jwt Token Generation"
)
@RestController
@RequestMapping("/api/v1/")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return jwtService.authenticateAndGetToken(authRequest);
    }
}
