package com.shaw.LibraryManagementSystem.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthFilter;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Mock
    private HttpSecurity httpSecurity;

    @InjectMocks
    private SecurityConfiguration securityConfiguration;

    @Test
    void testSecurityFilterChain() throws Exception {
        // Call the method under test
        SecurityFilterChain securityFilterChain = securityConfiguration.securityFilterChain(httpSecurity);

        // Verify that the security filter chain is properly configured
        verify(httpSecurity).csrf(AbstractHttpConfigurer::disable);
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).sessionManagement(any());
        verify(httpSecurity).authenticationProvider(authenticationProvider);
        verify(httpSecurity).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Ensure that the returned SecurityFilterChain is not null
        assert securityFilterChain != null;
    }
}