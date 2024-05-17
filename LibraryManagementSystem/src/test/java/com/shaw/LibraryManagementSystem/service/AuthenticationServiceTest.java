package com.shaw.LibraryManagementSystem.service;

import com.shaw.LibraryManagementSystem.dtos.AuthenticationRequest;
import com.shaw.LibraryManagementSystem.dtos.AuthenticationResponse;
import com.shaw.LibraryManagementSystem.dtos.RegisterRequest;
import com.shaw.LibraryManagementSystem.model.User;
import com.shaw.LibraryManagementSystem.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");

        User savedUser = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .password("password")
                .build();
        when(repository.save(any(User.class))).thenReturn(savedUser);

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertEquals("testToken", response.getToken());
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("test@example.com");
        authenticationRequest.setPassword("password");

        User user = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .password("password")
                .build();
        when(repository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("testToken");

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertEquals("testToken", response.getToken());
    }
}
