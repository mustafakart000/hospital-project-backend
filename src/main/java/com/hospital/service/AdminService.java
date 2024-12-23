package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hospital.dto.Response.AdminResponseList;
import com.hospital.entity.Admin;
import com.hospital.entity.User;
import com.hospital.repository.AdminRepository;


@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<AdminResponseList> getAllAdmin() {
        
        List<Admin> admins = adminRepository.findAll();

        return admins.stream()
                     .map((User user) -> AdminResponseList.builder()
                                                            .id(user.getId())
                                                            .username(user.getUsername())
                                                            .email(user.getEmail())
                                                            .phoneNumber(user.getTelefon())
                                                            .role(user.getRole())
                                                            .ad(user.getAd())
                                                            .soyad(user.getSoyad())
                                                            .build())
                     .toList();
    }
    public ResponseEntity<HttpStatus> deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin bulunamadı"));
        adminRepository.delete(admin);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    public AdminResponseList getAdminById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin bulunamadı"));
        return AdminResponseList.builder()
                                .id(admin.getId())
                                .username(admin.getUsername())
                                .email(admin.getEmail())
                                .phoneNumber(admin.getTelefon())
                                .role(admin.getRole())
                                .ad(admin.getAd())
                                .soyad(admin.getSoyad())
                                .build();
    }
}
