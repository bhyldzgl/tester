package com.haber.auth.service;

import com.haber.auth.dto.UserDTO;
import com.haber.auth.model.User;
import com.haber.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserDTO userDTO) {
        // Kullanıcı adının benzersiz olup olmadığını kontrol et
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Bu kullanıcı adı zaten kullanılıyor.");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Varsayılan rol atama
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_MEMBER"); // Yeni kullanıcıya varsayılan olarak "ROLE_MEMBER" rolü atanır
        user.setRoles(roles);

        return userRepository.save(user);
    }


}