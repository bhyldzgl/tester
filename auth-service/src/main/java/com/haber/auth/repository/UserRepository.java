package com.haber.auth.repository;

import com.haber.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Bu annotasyon, Spring'in bu interface'i bir bean olarak tanımasını sağlar
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}