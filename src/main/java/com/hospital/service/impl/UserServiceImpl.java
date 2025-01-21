package com.hospital.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.dto.DoctorRegisterRequest;
import com.hospital.dto.LoginRequest;
import com.hospital.dto.RegisterRequest;
import com.hospital.dto.UserDetailsResponse;
import com.hospital.dto.Response.LoginResponse;
import com.hospital.entity.Admin;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.exception.EmailAlreadyExistsException;
import com.hospital.exception.InvalidCredentialsException;
import com.hospital.exception.UserAlreadyExistsException;
import com.hospital.exception.UserValidationException;
import com.hospital.model.Role;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.UserRepository;
import com.hospital.security.JwtUtil;
import com.hospital.security.config.AuthenticationService;
import com.hospital.service.UserService;
import com.hospital.mapper.DoctorMapper;
import com.hospital.mapper.PatientMapper;
import com.hospital.mapper.AdminMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

// Kullanıcı işlemlerini yöneten servis sınıfı
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository; // Kullanıcı veritabanı işlemleri için
    private final PasswordEncoder passwordEncoder; // Şifreleme işlemleri için
    private final JwtUtil jwtUtil; // JWT token işlemleri için
    private final AuthenticationManager authenticationManager; // Kimlik doğrulama işlemleri için
    private final DoctorRepository doctorRepository; // Doktor veritabanı işlemleri için
    private final AuthenticationService authenticationService; // RSA şifre çözme için eklendi

    @PersistenceContext
    private EntityManager entityManager;

    

    @Override
    public void createAdminUser() {
        String adminPassword = "admin123";
        String adminTcKimlik = "admin";
        
        if (!userRepository.existsByUsername(adminTcKimlik)) {
            Admin adminUser = new Admin();
            adminUser.setUsername(adminTcKimlik);
            adminUser.setTcKimlik("22345678901");
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRole(Role.ADMIN.name());
            adminUser.setAd("Admin");
            adminUser.setSoyad("User");
            adminUser.setEmail("admin1@example.com");
            adminUser.setTelefon("1234567890");
            adminUser.setBirthDate(LocalDate.of(1990, 1, 1));
            adminUser.setKanGrubu("A Rh+");
            adminUser.setAdres("Admin Address");
                

            userRepository.save(adminUser);
            System.out.println("Admin kullanıcısı oluşturuldu: " + adminTcKimlik);
        }
    }
    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            // RSA ile şifrelenmiş şifreyi çöz
            String decryptedPassword = authenticationService.decryptPassword(request.getPassword());
            if (decryptedPassword == null) {
                throw new InvalidCredentialsException("Kullanıcı adı veya şifre hatalı");
            }
            
            // Çözülmüş şifre ile kimlik doğrulama yap
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), decryptedPassword)
            );
            
            // Kullanıcıyı veritabanından bul
            User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Kullanıcı bulunamadı"));
                
            // Kullanıcı için JWT token oluştur
            String token = jwtUtil.generateToken(user.getUsername());
            
            // Giriş yanıtı oluştur ve dön
            return LoginResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .token(token)
                .id(user.getId())
                .message("Giriş başarılı")
                .ad(user.getAd())
                .soyad(user.getSoyad())
                .build();
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Kullanıcı adı veya şifre hatalı");
        }
    }

    @Override
public void register(RegisterRequest request) {
    // TC Kimlik kontrolü
    if (userRepository.existsByTcKimlik(request.getTcKimlik())) {
        throw new UserAlreadyExistsException("Bu TC Kimlik numarası zaten kullanılıyor: " + request.getTcKimlik());
    } else if (userRepository.existsByEmail(request.getEmail())) {
        throw new EmailAlreadyExistsException("Bu email adresi zaten kullanılıyor: " + request.getEmail());
    }

    // PatientMapper kullanarak yeni hasta oluştur
    Patient patient = PatientMapper.mapToPatient(request, passwordEncoder);

    userRepository.save(patient);
}

@Override
@PreAuthorize("hasRole('ADMIN')")
public void registerAdmin(RegisterRequest request) {
    if(userRepository.existsByUsername(request.getUsername())) {
        throw new UserAlreadyExistsException("Bu kullanıcı adı zaten kullanılıyor");
    }
    if (userRepository.existsByTelefon(request.getTelefon())) {
        throw new UserAlreadyExistsException("Bu telefon numarası zaten kullanılıyor");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
        throw new EmailAlreadyExistsException("Bu email adresi zaten kullanılıyor");
    }
    
    Admin admin = AdminMapper.mapToAdmin(request, passwordEncoder);
    userRepository.save(admin);
}

@Override
public LoginResponse doctorLogin(LoginRequest request) {
    try {
        // RSA ile şifrelenmiş şifreyi çöz
        String decryptedPassword = authenticationService.decryptPassword(request.getPassword());
        if (decryptedPassword == null) {
            throw new InvalidCredentialsException("TC Kimlik veya şifre hatalı");
        }

        // Doktoru TC Kimlik ile bul
        Doctor doctor = doctorRepository.findByTcKimlik(request.getTcKimlik())
            .orElseThrow(() -> new InvalidCredentialsException("Doktor bulunamadı"));
           
        // Şifre kontrolü
        if (!passwordEncoder.matches(decryptedPassword, doctor.getPassword())) {
            throw new InvalidCredentialsException("TC Kimlik veya şifre hatalı");
        }
        
        if (!doctor.getRole().equals(Role.DOCTOR.name())) {
            throw new InvalidCredentialsException("Bu giriş sadece doktorlar içindir");
        }
        
        String token = jwtUtil.generateToken(doctor.getTcKimlik());
        
        return LoginResponse.builder()
            .role(doctor.getRole())
            .token(token)
            .id(doctor.getId())
            .message("Doktor girişi başarılı")
            .build();
    } catch (AuthenticationException e) {
        throw new InvalidCredentialsException("TC Kimlik veya şifre hatalı");
    }
}
@Override
public UserDetailsResponse getCurrentUserDetails(UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername())
        .map(u -> (User) u)
        .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    
    // Yeni bir JWT token oluştur
    String token = jwtUtil.generateToken(user.getUsername());
    
    return UserDetailsResponse.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole())
        .id(user.getId())
        .ad(user.getAd())
        .soyad(user.getSoyad())
        .token(token)  // Şifre yerine yeni oluşturulan token'ı kullan
        .build();
}

@Override
@PreAuthorize("hasRole('ADMIN')")
public void registerDoctor(DoctorRegisterRequest request) {
    validateUserUniqueness(request);
    Doctor doctor = DoctorMapper.mapToDoctor(request, passwordEncoder);
    userRepository.save(doctor);
}


public void validateUserUniqueness(DoctorRegisterRequest request) {
    List<String> errors = new ArrayList<>();
    
    if (userRepository.existsByUsername(request.getUsername())) {
        errors.add("Bu kullanıcı adı zaten kullanılıyor");
    }
    if (userRepository.existsByTcKimlik(request.getTcKimlik())) {
        errors.add("Bu TC Kimlik numarası zaten kayıtlı");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
        errors.add("Bu email adresi zaten kullanılıyor");
    }
    if (userRepository.existsByTelefon(request.getTelefon())) {
        errors.add("Bu telefon numarası zaten kayıtlı");
    }
    
    if (!errors.isEmpty()) {
        throw new UserValidationException(errors);
    }
}



}