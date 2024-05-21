package com.shaw.LibraryManagementSystem.config;

import com.shaw.LibraryManagementSystem.config.ApplicationConfig;
import com.shaw.LibraryManagementSystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Test
    public void testUserDetailsService() {
        // Create a mock user to return when findByEmail is called
        UserDetails userDetails = User.withUsername("test")
                .password("password")
                .roles("USER")
                .build();
        // Configure userRepository mock to return the mock user when findByEmail is called
        when(userRepository.findByEmail("test@example.com")).thenReturn((Optional) Optional.of(userDetails));

        // Invoke userDetailsService method and assert that it returns a non-null value
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        UserDetails loadedUser = userDetailsService.loadUserByUsername("test@example.com");
        assertNotNull(loadedUser);
    }
}