package com.hospital.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import com.hospital.repository.PatientRepository;
import com.hospital.dto.AdminByPatientRequest;
import com.hospital.dto.PatientRequest;
import com.hospital.dto.Response.PatientResponse;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.mapper.PatientMapper;
import com.hospital.entity.Patient;


@Service
public class PatientService {
    

    @Autowired
    private PatientRepository patientRepository;



    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;


    public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return PatientMapper.mapToPatientResponse(patient); 
    }

    public void updatePatient(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        PatientMapper.mapToPatient(patient, request);
        patientRepository.save(patient);
    }
    
}

    public void deletePatient(Long id) {
        if(patientRepository.existsById(id)){
            patientRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Patient not found");
        }
    }

    public Page<PatientResponse> getAllPatients(String searchTerm, Pageable pageable) {
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            return patientRepository
                .findByAdContainingIgnoreCaseOrSoyadContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTcKimlikContainingIgnoreCase(
                    searchTerm, searchTerm, searchTerm, searchTerm, pageable)
                .map(PatientMapper::mapToPatientResponse);
        }
        return patientRepository.findAll(pageable).map(PatientMapper::mapToPatientResponse);
    }

    public void updateByAdminPatient(Long id, AdminByPatientRequest request) {
        try {
            Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
            patient.setAd(request.getAd() != null ? request.getAd() : patient.getAd());
            patient.setSoyad(request.getSoyad() != null ? request.getSoyad() : patient.getSoyad());
            patient.setEmail(request.getEmail() != null ? request.getEmail() : patient.getEmail());
            patient.setTelefon(request.getTelefon() != null ? request.getTelefon() : patient.getTelefon());
            patient.setAdres(request.getAdres() != null ? request.getAdres() : patient.getAdres());
            patient.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : patient.getBirthDate());
            patient.setKanGrubu(request.getKanGrubu() != null ? request.getKanGrubu() : patient.getKanGrubu());
            patient.setTcKimlik(request.getTcKimlik() != null ? request.getTcKimlik() : patient.getTcKimlik());
            patient.setPassword(request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : patient.getPassword());
            patient.setUsername(request.getUsername() != null ? request.getUsername() : patient.getUsername());
            patientRepository.save(patient);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Bu email adresi zaten kullanılıyor. Lütfen başka bir email adresi giriniz.");
        }
    }

}

