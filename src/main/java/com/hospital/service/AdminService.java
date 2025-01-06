package com.hospital.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.dto.AdminByAdminRequest;
import com.hospital.dto.Response.AdminResponseList;
import com.hospital.entity.Admin;
import com.hospital.entity.User;
import com.hospital.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class AdminService {
    
    private final AdminRepository adminRepository;

    
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
                                .ad(admin.getAd())
                                .soyad(admin.getSoyad())
                                .address(admin.getAdres())
                                .birthDate(admin.getBirthDate())
                                .tcKimlik(admin.getTcKimlik())
                                .kanGrubu(admin.getKanGrubu())
                                .build();
    }
    
    public void updateAdmin(Long id, AdminByAdminRequest request) {
        try {
            Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
            
            admin.setAd(request.getAd() != null ? request.getAd() : admin.getAd());
            admin.setSoyad(request.getSoyad() != null ? request.getSoyad() : admin.getSoyad());
            admin.setEmail(request.getEmail() != null ? request.getEmail() : admin.getEmail());
            admin.setUsername(request.getUsername() != null ? request.getUsername() : admin.getUsername());
            admin.setTelefon(request.getTelefon() != null ? request.getTelefon() : admin.getTelefon());
            admin.setAdres(request.getAdres() != null ? request.getAdres() : admin.getAdres());
            admin.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : admin.getBirthDate());
            admin.setKanGrubu(request.getKanGrubu() != null ? request.getKanGrubu() : admin.getKanGrubu());
            admin.setTcKimlik(request.getTcKimlik() != null ? request.getTcKimlik() : admin.getTcKimlik());
            admin.setPassword(request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : admin.getPassword());
            
            adminRepository.save(admin);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Bu email adresi zaten kullanılıyor. Lütfen başka bir email adresi giriniz.");
        }
    }
}
