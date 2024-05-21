package com.shaw.LibraryManagementSystem.service;

import com.shaw.LibraryManagementSystem.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Test
    public void testGenerateToken() {
        UserDetails userDetails = User.withUsername("test").password("password").roles("USER").build();

        String token = jwtService.generateToken(userDetails);

        Claims claims = Jwts.parser().setSigningKey(jwtService.getSignInKey()).parseClaimsJws(token).getBody();
        //.parseClaimsJws(token).getBody();
        assertEquals("test", claims.getSubject());
    }

    @Test
    public void testIsTokenValid() {
        UserDetails userDetails = User.withUsername("test").password("password").roles("USER").build();
        String token = jwtService.generateToken(userDetails);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertEquals(true, isValid);
    }
}