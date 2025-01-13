package com.sahil.globe.auth.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import com.sahil.globe.auth.dto.AuthRequest;
import com.sahil.globe.auth.dto.AuthResponse;
import com.sahil.globe.auth.dto.RegisterRequest;
import com.sahil.globe.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> home() {
        return ResponseEntity.ok(String.format(
                "Auth Globe API%n%n" +
                        "Welcome to the auth endpoint, you can make the following requests:%n" +
                        "- POST /register%n" +
                        "- POST /authenticate%n"));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
