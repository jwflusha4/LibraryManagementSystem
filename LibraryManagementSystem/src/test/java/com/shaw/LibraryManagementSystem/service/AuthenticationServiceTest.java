package com.shaw.LibraryManagementSystem.service;

import com.shaw.LibraryManagementSystem.dtos.AuthenticationRequest;
import com.shaw.LibraryManagementSystem.dtos.AuthenticationResponse;
import com.shaw.LibraryManagementSystem.dtos.RegisterRequest;
import com.shaw.LibraryManagementSystem.model.Role;
import com.shaw.LibraryManagementSystem.model.User;
import com.shaw.LibraryManagementSystem.repository.UserRepository;
import com.shaw.LibraryManagementSystem.service.AuthenticationService;
import com.shaw.LibraryManagementSystem.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository repository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john@example.com");
        registerRequest.setPassword("password");

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(repository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("generatedToken");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertEquals("generatedToken", response.getToken());
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("john@example.com");
        authenticationRequest.setPassword("password");

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(repository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("generatedToken");

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertEquals("generatedToken", response.getToken());
    }
}