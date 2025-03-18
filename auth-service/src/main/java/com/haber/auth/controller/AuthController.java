package com.haber.auth.controller;

import java.util.Set;
import java.util.HashSet;
import com.haber.auth.dto.UserDTO;
import com.haber.auth.model.User;
import com.haber.auth.repository.UserRepository;
import com.haber.auth.security.JwtUtil;
import com.haber.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired // UserRepository bean'ini enjekte ediyoruz
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Varsayılan rol atama
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_MEMBER"); // Yeni kullanıcıya varsayılan olarak "ROLE_MEMBER" rolü atanır
        user.setRoles(roles);

        userRepository.save(user);
        return ResponseEntity.ok("Kullanıcı başarıyla kaydedildi");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
        if (passwordEncoder.matches(userDTO.getPassword(), userDetails.getPassword())) {
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } else {
            throw new BadCredentialsException("Kullanıcı adı veya şifre hatalı");
        }
    }
}