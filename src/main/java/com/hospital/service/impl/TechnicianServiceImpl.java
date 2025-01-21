package com.hospital.service.impl;

import com.hospital.dto.TechnicianRequest;
import com.hospital.dto.TechnicianResponse;
import com.hospital.entity.Technician;
import com.hospital.mapper.TechnicianMapper;
import com.hospital.repository.TechnicianRepository;
import com.hospital.repository.UserRepository;
import com.hospital.service.TechnicianService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicianServiceImpl implements TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;
    private final TechnicianMapper technicianMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TechnicianResponse createTechnician(TechnicianRequest request) {
        // TC Kimlik kontrolü - tüm kullanıcılar için
        if (userRepository.existsByTcKimlik(request.getTcKimlik())) {
            throw new DataIntegrityViolationException("Bu TC Kimlik numarası ile kayıtlı bir kullanıcı zaten mevcut.");
        }

        // Email kontrolü - tüm kullanıcılar için
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityViolationException("Bu e-posta adresi ile kayıtlı bir kullanıcı zaten mevcut.");
        }

        // Kullanıcı adı kontrolü - tüm kullanıcılar için
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DataIntegrityViolationException("Bu kullanıcı adı ile kayıtlı bir kullanıcı zaten mevcut.");
        }

        try {
            Technician technician = technicianMapper.toEntity(request);
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            technician.setPassword(hashedPassword);
            Technician savedTechnician = technicianRepository.save(technician);
            return technicianMapper.toResponse(savedTechnician);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Teknisyen kaydı yapılırken bir hata oluştu. Lütfen tüm zorunlu alanları kontrol ediniz.");
        }
    }

    @Override
    public List<TechnicianResponse> getAllTechnicians() {
        return technicianRepository.findAll().stream()
                .map(technicianMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TechnicianResponse getTechnicianById(Long id) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Technician not found with id: " + id));
        return technicianMapper.toResponse(technician);
    }

    @Override
    public TechnicianResponse updateTechnician(Long id, TechnicianRequest request) {
        Technician existingTechnician = technicianRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teknisyen bulunamadı. ID: " + id));
        
        // TC Kimlik kontrolü - tüm kullanıcılar için
        if (request.getTcKimlik() != null && !request.getTcKimlik().equals(existingTechnician.getTcKimlik()) &&
            userRepository.existsByTcKimlik(request.getTcKimlik())) {
            throw new DataIntegrityViolationException("Bu TC Kimlik numarası ile kayıtlı bir kullanıcı zaten mevcut.");
        }

        // Email kontrolü - tüm kullanıcılar için
        if (request.getEmail() != null && !request.getEmail().equals(existingTechnician.getEmail()) &&
            userRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityViolationException("Bu e-posta adresi ile kayıtlı bir kullanıcı zaten mevcut.");
        }

        // Kullanıcı adı kontrolü - tüm kullanıcılar için
        if (request.getUsername() != null && !request.getUsername().equals(existingTechnician.getUsername()) &&
            userRepository.existsByUsername(request.getUsername())) {
            throw new DataIntegrityViolationException("Bu kullanıcı adı ile kayıtlı bir kullanıcı zaten mevcut.");
        }

        try {
            if (request.getName() != null) {
                existingTechnician.setAd(request.getName());
            }
            if (request.getSurname() != null) {
                existingTechnician.setSoyad(request.getSurname());
            }
            if (request.getEmail() != null) {
                existingTechnician.setEmail(request.getEmail());
            }
            if (request.getUsername() != null) {
                existingTechnician.setUsername(request.getUsername());
            }
            if (request.getTcKimlik() != null) {
                existingTechnician.setTcKimlik(request.getTcKimlik());
            }
            if (request.getAdres() != null) {
                existingTechnician.setAdres(request.getAdres());
            }
            if (request.getKanGrubu() != null) {
                existingTechnician.setKanGrubu(request.getKanGrubu());
            }
            if (request.getBirthDate() != null) {
                existingTechnician.setBirthDate(request.getBirthDate());
            }
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(request.getPassword());
                existingTechnician.setPassword(hashedPassword);
            }
            if (request.getPhoneNumber() != null) {
                existingTechnician.setTelefon(request.getPhoneNumber());
            }
            if (request.getDepartment() != null) {
                existingTechnician.setDepartment(request.getDepartment());
            }
            if (request.getSpecialization() != null) {
                existingTechnician.setSpecialization(request.getSpecialization());
            }
            
            Technician updatedTechnician = technicianRepository.save(existingTechnician);
            return technicianMapper.toResponse(updatedTechnician);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Teknisyen güncellenirken bir hata oluştu. Lütfen girilen bilgileri kontrol ediniz.");
        }
    }

    @Override
    public void deleteTechnician(Long id) {
        if (!technicianRepository.existsById(id)) {
            throw new EntityNotFoundException("Technician not found with id: " + id);
        }
        technicianRepository.deleteById(id);
    }
} 