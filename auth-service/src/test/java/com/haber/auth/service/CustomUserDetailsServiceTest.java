package com.haber.auth.service;

import com.haber.auth.model.User;
import com.haber.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsername() {
        // Test verileri
        String username = "testuser";
        String password = "password";
        Set<String> roles = Set.of("ROLE_MEMBER");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);

        // Mock davranışları
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Metodu çağır
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Sonuçları doğrula
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Test verileri
        String username = "nonexistentuser";

        // Mock davranışları
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Metodu çağır ve istisna bekleyin
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        verify(userRepository, times(1)).findByUsername(username);
    }
}