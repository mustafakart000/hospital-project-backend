package com.hospital.repository;

import com.hospital.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    boolean existsByTcKimlik(String tcKimlik);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
} 