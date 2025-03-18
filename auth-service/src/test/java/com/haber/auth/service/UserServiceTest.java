package com.haber.auth.service;

import com.haber.auth.dto.UserDTO;
import com.haber.auth.model.User;
import com.haber.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        // Test verileri
        String username = "testuser";
        String password = "password";
        String encodedPassword = "encodedPassword";

        // UserDTO oluştur
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        // Mock davranışları
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Metodu çağır
        User user = userService.createUser(userDTO); // UserDTO parametresi ile çağır

        // Sonuçları doğrula
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(encodedPassword, user.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }
}