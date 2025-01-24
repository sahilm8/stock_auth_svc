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
import com.sahil.stock.auth.model.PortfolioUser;
import com.sahil.stock.auth.model.User;
import com.sahil.stock.auth.repository.auth.UserRepository;
import com.sahil.stock.auth.repository.portfolio.PortfolioUserRepository;
import com.sahil.stock.auth.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository userRepository;
        private final PortfolioUserRepository portfolioUserRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
                if (userRepository.existsByEmail(registerUserRequest.getEmail())) {
                        throw new UserAlreadyExistsException("User already exists: " + registerUserRequest.getEmail());
                }

                User user = User.builder()
                                .firstName(registerUserRequest.getFirstName())
                                .lastName(registerUserRequest.getLastName())
                                .email(registerUserRequest.getEmail())
                                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                                .build();

                User savedUser = userRepository.save(user);
                UserPrincipal userPrincipal = UserPrincipal.from(savedUser);

                PortfolioUser portfolioUser = PortfolioUser
                                .builder()
                                .firstName(registerUserRequest.getFirstName())
                                .lastName(registerUserRequest.getLastName())
                                .email(registerUserRequest.getEmail())
                                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                                .build();

                portfolioUserRepository.save(portfolioUser);

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

                User user = userRepository.findByEmail(authenticateUserRequest.getEmail())
                                .orElseThrow(() -> new UserNotFoundException(
                                                "User not found: " + authenticateUserRequest.getEmail()));

                UserPrincipal userPrincipal = UserPrincipal.from(user);

                return AuthenticateUserResponse.builder()
                                .accessToken(jwtService.generateToken(userPrincipal))
                                .refreshToken(jwtService.generateRefreshToken(userPrincipal))
                                .build();
        }

        public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
                String refreshToken = refreshTokenRequest.getRefreshToken();
                String email = jwtService.extractClaim(refreshToken, Claims::getSubject);

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

                UserPrincipal userPrincipal = UserPrincipal.from(user);

                if (!jwtService.isTokenValid(refreshToken, userPrincipal)) {
                        throw new ExpiredJwtException(null, null, null);
                }

                String accessToken = jwtService.generateToken(userPrincipal);
                return RefreshTokenResponse.builder()
                                .accessToken(accessToken)
                                .build();
        }
}
