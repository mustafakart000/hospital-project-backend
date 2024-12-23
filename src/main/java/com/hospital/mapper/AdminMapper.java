package com.hospital.mapper;

import com.hospital.dto.RegisterRequest;
import com.hospital.entity.Admin;
import com.hospital.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminMapper {
    
    public static Admin mapToAdmin(RegisterRequest request, PasswordEncoder passwordEncoder) {
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Role.ADMIN.name());
        admin.setAd(request.getAd());
        admin.setSoyad(request.getSoyad());
        admin.setEmail(request.getEmail());
        admin.setTelefon(request.getTelefon());
        admin.setAdres(request.getAdres());
        admin.setBirthDate(request.getBirthDate());
        admin.setTcKimlik(request.getTcKimlik());
        admin.setKanGrubu(request.getKanGrubu());
        return admin;
    }
} 