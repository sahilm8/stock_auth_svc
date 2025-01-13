package com.sahil.globe.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sahil.globe.auth.dto.AuthRequest;
import com.sahil.globe.auth.dto.AuthResponse;
import com.sahil.globe.auth.dto.RegisterRequest;
import com.sahil.globe.auth.exception.UserAlreadyExistsException;
import com.sahil.globe.auth.exception.UserNotFoundException;
import com.sahil.globe.auth.model.User;
import com.sahil.globe.auth.repository.UserRepository;
import com.sahil.globe.auth.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);
        UserPrincipal principal = UserPrincipal.from(savedUser);
        
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(principal))
                .refreshToken(jwtService.generateRefreshToken(principal))
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password.");
        }
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getEmail()));
        
        UserPrincipal principal = UserPrincipal.from(user);
        
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(principal))
                .refreshToken(jwtService.generateRefreshToken(principal))
                .build();
    }
}
