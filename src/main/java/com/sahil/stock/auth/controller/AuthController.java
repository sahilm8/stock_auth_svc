package com.sahil.stock.auth.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.stock.auth.dto.authenticateUser.AuthenticateUserRequest;
import com.sahil.stock.auth.dto.authenticateUser.AuthenticateUserResponse;
import com.sahil.stock.auth.dto.registerUser.RegisterUserRequest;
import com.sahil.stock.auth.dto.registerUser.RegisterUserResponse;
import com.sahil.stock.auth.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> home() {
        return ResponseEntity.ok(String.format(
                "Stock Auth API%n%n" +
                        "Welcome to the /auth endpoint, you can make the following requests:%n" +
                        "- POST /register-user%n" +
                        "- POST /authenticate-user%n"));
    }

    @PostMapping(value = "/register-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterUserResponse> registerUser(
            @Valid @RequestBody RegisterUserRequest registerUserRequest) {
        return ResponseEntity.ok(authService.registerUser(registerUserRequest));
    }

    @PostMapping(value = "/authenticate-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticateUserResponse> authenticateUser(
            @Valid @RequestBody AuthenticateUserRequest authenticateUserRequest) {
        return ResponseEntity.ok(authService.authenticateUser(authenticateUserRequest));
    }

    @GetMapping(value = "/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> invalid() {
        return ResponseEntity
                .ok("Invalid request, please refer to the README at https://github.com/sahilm8/stock_auth_svc");
    }
}
