package com.famipam.security.auth;

import com.famipam.security.config.JwtService;
import com.famipam.security.entity.User;
import com.famipam.security.exception.ResourceNotFoundException;
import com.famipam.security.repository.UserRepository;
import com.famipam.security.util.DateUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .birthdate(request.getBirthdate())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(DateUtils.formatDefaultPassword(request.getBirthdate())))
                .build();
        if (repository.findByUsername(user.getUsername()).isEmpty()) {
            repository.save(user);
        }

        return AuthenticationResponse.builder()
                .token(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        return AuthenticationResponse.builder()
                .token(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        if (!jwtService.isRefreshToken(request.getToken())) throw new JwtException("Please provide valid refresh token");

        String username = jwtService.extractUsername(request.getToken());
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found."));

        return AuthenticationResponse.builder()
                .token(jwtService.generateAccessToken(user))
                .refreshToken(request.getToken())
                .build();
    }
}
