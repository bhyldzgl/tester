package com.haber.auth.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    public void testGenerateToken() {
        // Test verileri
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());

        // Metodu çağır
        String token = jwtUtil.generateToken(userDetails);

        // Sonuçları doğrula
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testValidateToken() {
        // Test verileri
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        String token = jwtUtil.generateToken(userDetails);

        // Metodu çağır
        boolean isValid = jwtUtil.validateToken(token, userDetails);

        // Sonuçları doğrula
        assertTrue(isValid);
    }
}