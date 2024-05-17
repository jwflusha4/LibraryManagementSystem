package com.shaw.LibraryManagementSystem.service;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.shaw.LibraryManagementSystem.dtos.AuthenticationRequest;
import com.shaw.LibraryManagementSystem.dtos.AuthenticationResponse;
import com.shaw.LibraryManagementSystem.dtos.RegisterRequest;
import com.shaw.LibraryManagementSystem.model.Role;
import com.shaw.LibraryManagementSystem.model.User;
import com.shaw.LibraryManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);

        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        saveUserToken(savedUser, jwtToken);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}

