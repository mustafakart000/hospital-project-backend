package com.hospital.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.dto.AdminByDoctorRequest;
import com.hospital.dto.DoctorRequest;
import com.hospital.dto.Response.DoctorResponseList;
import com.hospital.dto.Response.GetFullDoctorResponse;
import com.hospital.entity.Doctor;
import com.hospital.exception.NotFoundException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.mapper.DoctorMapper;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.DoctorSpecialityRepository;
import com.hospital.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import com.hospital.model.DoctorSpeciality;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class DoctorService {

    private final DoctorSpecialityRepository doctorSpecialityRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorSpecialityRepository doctorSpecialityRepository, 
                            UserRepository userRepository,
                            DoctorRepository doctorRepository,
                            
                            DoctorMapper doctorMapper,
                            PasswordEncoder passwordEncoder) {
        this.doctorSpecialityRepository = doctorSpecialityRepository;
        
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.passwordEncoder = passwordEncoder;
        
    }

    @NonNull
    public List<Map<String, String>> getAllSpecialties() {
        return doctorSpecialityRepository.findAll()
            .stream()
            .map(speciality -> Map.of("id", speciality.getId().toString(), "name", speciality.getDisplayName()))
            .toList();
    }

    public List<DoctorResponseList> getAllDoctors() {
        return doctorRepository.findAll().stream()
        .map(doctor -> DoctorResponseList.builder()
            .id(doctor.getId())
            .ad(doctor.getAd())
            .soyad(doctor.getSoyad())
            .speciality(doctor.getUzmanlik().getDisplayName())
            .email(doctor.getEmail())
            .phone(doctor.getTelefon())
            .address(doctor.getAdres())
            .build())
        .toList();
    }
    
    public GetFullDoctorResponse getDoctorById(Long id) {
        return doctorRepository.findById(id)
            .map(doctor -> GetFullDoctorResponse.builder()
                .username(doctor.getUsername())
                .id(doctor.getId())
                .ad(doctor.getAd())
                .soyad(doctor.getSoyad())
                .speciality(doctor.getUzmanlik().getDisplayName())
                .email(doctor.getEmail())
                .phone(doctor.getTelefon())
                .address(doctor.getAdres())
                .birthDate(doctor.getBirthDate())
                .tcKimlik(doctor.getTcKimlik())
                .kanGrubu(doctor.getKanGrubu())
                .diplomaNo(doctor.getDiplomaNo())
                .unvan(doctor.getUnvan())
                .build())
            .orElseThrow(() -> new NotFoundException("Doctor not found"));
    }

 
    public void deleteDoctor(Long id) {
        doctorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctorRepository.deleteById(id);
    }
    @Transactional
    public ResponseEntity<HttpStatus> updateDoctor(Long id, DoctorRequest doctorRequest) {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctorMapper.toDoctor(doctor, doctorRequest);
        doctorRepository.save(doctor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public void updateByAdminDoctor(Long id, AdminByDoctorRequest request) {
        try {
            Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
            
            doctor.setAd(request.getAd() != null ? request.getAd() : doctor.getAd());
            doctor.setSoyad(request.getSoyad() != null ? request.getSoyad() : doctor.getSoyad());
            doctor.setEmail(request.getEmail() != null ? request.getEmail() : doctor.getEmail());
            doctor.setUsername(request.getUsername() != null ? request.getUsername() : doctor.getUsername());
            doctor.setTelefon(request.getTelefon() != null ? request.getTelefon() : doctor.getTelefon());
            doctor.setAdres(request.getAdres() != null ? request.getAdres() : doctor.getAdres());
            doctor.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : doctor.getBirthDate());
            doctor.setKanGrubu(request.getKanGrubu() != null ? request.getKanGrubu() : doctor.getKanGrubu());
            doctor.setTcKimlik(request.getTcKimlik() != null ? request.getTcKimlik() : doctor.getTcKimlik());
            doctor.setDiplomaNo(request.getDiplomaNo() != null ? request.getDiplomaNo() : doctor.getDiplomaNo());
            doctor.setUnvan(request.getUnvan() != null ? request.getUnvan() : doctor.getUnvan());
            doctor.setUzmanlik(request.getUzmanlik() != null ? DoctorSpeciality.valueOf(request.getUzmanlik()) : doctor.getUzmanlik());

            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                doctor.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            
            doctorRepository.save(doctor);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Bu email adresi zaten kullanılıyor. Lütfen başka bir email adresi giriniz.");
        }
    }
       
}
