package com.hospital.service.impl;

import com.hospital.dto.DoctorRegisterRequest;
import com.hospital.dto.LoginRequest;
import com.hospital.dto.LoginResponse;
import com.hospital.dto.RegisterRequest;
import com.hospital.entity.Admin;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.exception.UserAlreadyExistsException;
import com.hospital.model.Role;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.UserRepository;
import com.hospital.security.JwtUtil;
import com.hospital.service.UserService;
import lombok.RequiredArgsConstructor;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

// Kullanıcı işlemlerini yöneten servis sınıfı
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository; // Kullanıcı veritabanı işlemleri için
    private final PasswordEncoder passwordEncoder; // Şifreleme işlemleri için
    private final JwtUtil jwtUtil; // JWT token işlemleri için
    private final AuthenticationManager authenticationManager; // Kimlik doğrulama işlemleri için
    private final DoctorRepository doctorRepository; // Doktor veritabanı işlemleri için
    @Override
    public void createAdminUser() {
        String adminUsername = "admin";
        String adminPassword = "admin123"; // Şifreyi şifreleyin
        String adminAd = "Admin"; // Admin adı
        String adminSoyad = "User"; // Admin soyadı
        String adminEmail = "admin@example.com"; // Admin e-posta
        String adminTelefon = "1234567890"; // Admin telefon
        LocalDate adminBirthDate = LocalDate.of(1990, 1, 1); // Admin doğum tarihi

        if (!userRepository.existsByUsername(adminUsername)) {
            Admin adminUser = new Admin();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode(adminPassword)); // Şifreyi şifreleyin
            adminUser.setAd(adminAd);
            adminUser.setSoyad(adminSoyad);
            adminUser.setEmail(adminEmail);
            adminUser.setTelefon(adminTelefon);
            adminUser.setBirthDate(adminBirthDate); // Doğum tarihini ayarlayın
            adminUser.setRole(Role.ADMIN.name());
            userRepository.save(adminUser);
        }
    }
    @Override
    public LoginResponse login(LoginRequest request) {
        // Kullanıcı kimlik doğrulaması yapar
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        // Kullanıcıyı veritabanından bulur
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
            
        // Kullanıcı için JWT token oluşturur
        String token = jwtUtil.generateToken(user.getUsername());
        
        // Giriş yanıtı oluşturur ve döner
        return LoginResponse.builder()
            .username(user.getUsername())
            .role(user.getRole())
            .token(token)
            .message("Giriş başarılı")
            .build();
    }

    @Override
public void register(RegisterRequest request) {
    // Kullanıcı adı zaten varsa hata fırlatır
    if (userRepository.existsByUsername(request.getUsername())) {
        throw new UserAlreadyExistsException("Bu kullanıcı adı zaten kullanılıyor");
    }

    // Yeni kullanıcı oluşturur ve kaydeder
    User patient = new Patient();
    
    // Temel bilgileri ayarla
    patient.setUsername(request.getUsername());
    patient.setPassword(passwordEncoder.encode(request.getPassword())); // Şifreyi şifrele
    patient.setRole(Role.PATIENT.name()); // Kullanıcının rolünü ata

    // Ek bilgileri User nesnesine aktar
    patient.setAd(request.getAd());
    patient.setSoyad(request.getSoyad());
    patient.setEmail(request.getEmail());
    patient.setTelefon(request.getTelefon());
    patient.setAdres(request.getAdres());
    patient.setBirthDate(request.getBirthDate()); // Doğum tarihini ekle
    patient.setKanGrubu(request.getKanGrubu()); // Kan grubunu ekle

    // Kullanıcıyı veritabanına kaydet
    userRepository.save(patient);
}


@Override
@PreAuthorize("hasRole('ADMIN')")
public void registerDoctor(DoctorRegisterRequest request) {
    // Kullanıcı adı zaten varsa hata fırlatır
    if (userRepository.existsByUsername(request.getUsername())) {
        throw new UserAlreadyExistsException("Bu kullanıcı adı zaten kullanılıyor");
    }

    // Yeni doktor nesnesi oluştur ve bilgileri aktar
    Doctor doctor = new Doctor();
    doctor.setUsername(request.getUsername());
    doctor.setPassword(passwordEncoder.encode(request.getPassword())); // Şifreyi şifrele
    doctor.setRole(Role.DOCTOR.name()); // Doktor rolünü ata

    // Ek bilgileri aktarma
    doctor.setAd(request.getAd());
    doctor.setSoyad(request.getSoyad());
    doctor.setUzmanlik(request.getUzmanlik()); // Uzmanlık alanını ekle
    doctor.setDiplomaNo(request.getDiplomaNo()); // Diploma numarasını ekle
    doctor.setUnvan(request.getUnvan()); // Unvanı ekle
    doctor.setTelefon(request.getTelefon());
    doctor.setEmail(request.getEmail());
    doctor.setAdres(request.getAdres()); // Adresi ekle
    doctor.setBirthDate(request.getBirthDate()); // Doğum tarihini ekle
    doctor.setKanGrubu(request.getKanGrubu()); // Kan grubunu ekle

    // Doktoru veritabanına kaydet
    userRepository.save(doctor);
}
@Override
@PreAuthorize("hasRole('ADMIN')")
public void registerAdmin(RegisterRequest request) {
      // Kullanıcı adı zaten varsa hata fırlatır
      if (userRepository.existsByUsername(request.getUsername())) {
        throw new UserAlreadyExistsException("Bu kullanıcı adı zaten kullanılıyor");
    }
    // Admin kaydı işlemleri burada gerçekleştirilebilir
    Admin admin = new Admin();
    admin.setUsername(request.getUsername());
    admin.setPassword(passwordEncoder.encode(request.getPassword())); // Şifreyi şifrele
    admin.setRole(Role.ADMIN.name()); // Admin rolünü ata
    admin.setAd(request.getAd());
    admin.setSoyad(request.getSoyad());
    admin.setEmail(request.getEmail());
    admin.setTelefon(request.getTelefon());
    admin.setAdres(request.getAdres());
    admin.setBirthDate(request.getBirthDate());
    
    userRepository.save(admin);
}

@Override
public LoginResponse doctorLogin(LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
    
    Doctor doctor = doctorRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));
    
    String token = jwtUtil.generateToken(doctor.getUsername());
    
    return LoginResponse.builder()
        .username(doctor.getUsername())
        .role(doctor.getRole())
        .token(token)
        .message("Doktor girişi başarılı")
        .build();
}

}