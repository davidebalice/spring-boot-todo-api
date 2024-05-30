package com.todoapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "REST APIs for CSRF Token",
        description = "CSRF Token Generation"
)
@RestController
public class CsrfController {

    @PostMapping("/api/v1/csrf")
    public ResponseEntity<CsrfToken> getCsrfToken(CsrfToken token) {
        return ResponseEntity.ok(token);
    }
}
