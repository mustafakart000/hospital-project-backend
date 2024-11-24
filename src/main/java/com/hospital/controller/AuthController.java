package com.hospital.controller;

import com.hospital.dto.DoctorRegisterRequest;
import com.hospital.dto.LoginRequest;
import com.hospital.dto.LoginResponse;
import com.hospital.dto.RegisterRequest;
import com.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


// Kimlik doğrulama işlemlerini yöneten REST kontrolcüsü
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService; // Kullanıcı servis sınıfı

   
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Kullanıcı giriş isteğini işler ve yanıt döner
        return ResponseEntity.ok(userService.login(request));
    }
    @PostMapping("/doctor/login")
    public ResponseEntity<LoginResponse> doctorLogin(@RequestBody LoginRequest request) {
        // Kullanıcı giriş isteğini işler ve yanıt döner
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerPatient(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/doctor/register")
    public ResponseEntity<Void> addDoctorByAdmin(@RequestBody DoctorRegisterRequest request) {
        userService.registerDoctor(request);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/register")
    public ResponseEntity<Void> addAdminByAdmin(@RequestBody RegisterRequest request) {
        userService.registerAdmin(request);
        return ResponseEntity.ok().build();
    }

}