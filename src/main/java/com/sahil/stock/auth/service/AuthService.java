package com.sahil.stock.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sahil.stock.auth.dto.authenticateUser.AuthenticateUserRequest;
import com.sahil.stock.auth.dto.authenticateUser.AuthenticateUserResponse;
import com.sahil.stock.auth.dto.refreshToken.RefreshTokenRequest;
import com.sahil.stock.auth.dto.refreshToken.RefreshTokenResponse;
import com.sahil.stock.auth.dto.registerUser.RegisterUserRequest;
import com.sahil.stock.auth.dto.registerUser.RegisterUserResponse;
import com.sahil.stock.auth.exception.UserAlreadyExistsException;
import com.sahil.stock.auth.exception.UserNotFoundException;
import com.sahil.stock.auth.model.AuthUser;
import com.sahil.stock.auth.repository.AuthUserRepository;
import com.sahil.stock.auth.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        if (authUserRepository.existsByEmail(registerUserRequest.getEmail())) {
            throw new UserAlreadyExistsException("User already exists: " + registerUserRequest.getEmail());
        }

        AuthUser authUser = AuthUser.builder()
                .firstName(registerUserRequest.getFirstName())
                .lastName(registerUserRequest.getLastName())
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .build();

        AuthUser savedUser = authUserRepository.save(authUser);
        UserPrincipal userPrincipal = UserPrincipal.from(savedUser);

        return RegisterUserResponse.builder()
                .accessToken(jwtService.generateToken(userPrincipal))
                .refreshToken(jwtService.generateRefreshToken(userPrincipal))
                .build();
    }

    public AuthenticateUserResponse authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticateUserRequest.getEmail(),
                            authenticateUserRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }

        AuthUser authUser = authUserRepository.findByEmail(authenticateUserRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + authenticateUserRequest.getEmail()));

        UserPrincipal userPrincipal = UserPrincipal.from(authUser);

        return AuthenticateUserResponse.builder()
                .accessToken(jwtService.generateToken(userPrincipal))
                .refreshToken(jwtService.generateRefreshToken(userPrincipal))
                .build();
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String email = jwtService.extractClaim(refreshToken, Claims::getSubject);

        AuthUser authUser = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        UserPrincipal userPrincipal = UserPrincipal.from(authUser);

        if (!jwtService.isTokenValid(refreshToken, userPrincipal)) {
            throw new ExpiredJwtException(null, null, null);
        }

        String accessToken = jwtService.generateToken(userPrincipal);
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
