package com.hospital.repository;

import com.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Kullanıcı veritabanı işlemleri için JPA deposu
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Kullanıcı adını kullanarak kullanıcıyı bulur
    Boolean existsByUsername(String username); // Kullanıcı adının var olup olmadığını kontrol eder
}