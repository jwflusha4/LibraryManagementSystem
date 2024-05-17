package com.shaw.LibraryManagementSystem.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    @Mock
    private UserDetails userDetails;

    private JwtService jwtService;
    private SecretKey key;
    private String secretString;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        secretString = io.jsonwebtoken.io.Encoders.BASE64.encode(key.getEncoded());
        jwtService = new JwtService() {
            @Override
            public Key getSignInKey() {
                byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secretString);
                return Keys.hmacShaKeyFor(keyBytes);
            }
        };

        // Mock UserDetails
        when(userDetails.getUsername()).thenReturn("testUser");
    }

    @Test
    void testExtractUserName() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUserName(token);
        assertEquals("testUser", username);
    }

    @Test
    void testExtractClaim() {
        String token = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals("testUser", claims.getSubject());
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testGenerateTokenWithExtraClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");
        String token = jwtService.generateToken(extraClaims, userDetails);
        assertNotNull(token);

        Claims claims = jwtService.extractAllClaims(token);
        assertEquals("testUser", claims.getSubject());
        assertEquals("ADMIN", claims.get("role"));
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testIsTokenExpired() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // Expired token
                .signWith(jwtService.getSignInKey())
                .compact();

        // Allow some clock skew to handle minor timing differences
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
            jwtService.extractAllClaims(token);
        });
    }

    @Test
    void testExtractExpiration() {
        String token = jwtService.generateToken(userDetails);
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void testExtractAllClaims() {
        String token = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals("testUser", claims.getSubject());
    }
}