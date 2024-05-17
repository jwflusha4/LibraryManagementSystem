package com.shaw.LibraryManagementSystem.config;

import com.shaw.LibraryManagementSystem.model.User;
import com.shaw.LibraryManagementSystem.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Test
    public void testUserDetailsService() {
        // Create a mock user
        User user = new User();
        user.setEmail("test@example.com");

        // Mock the userRepository.findByEmail() method to return the mock user
        when(userRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(user));

        // Test the userDetailsService() method
        UserDetails userDetails = applicationConfig.userDetailsService().loadUserByUsername("test@example.com");
        assertNotNull(userDetails);

        // Verify that the userRepository.findByEmail() method was called
        verify(userRepository).findByEmail("test@example.com");
    }
}
