package com.todoapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todoapi.config.CustomException;

@RestController
@RequestMapping("/api/v1/")
public class WelcomeController {

 
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Index page");
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login page");
    }

    @GetMapping("/testlogin")
    public ResponseEntity<String> testlogin() {
        return ResponseEntity.ok("Test protected page");
    }

    @GetMapping("/error")
    public String error() {
        throw new CustomException(500, "Personalized error");
    }
}
