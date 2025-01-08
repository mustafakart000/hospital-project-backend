package com.hospital.service;

import com.hospital.entity.Prescription;
import com.hospital.repository.PrescriptionRepository;

import com.hospital.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.hospital.dto.request.PrescriptionRequest;
import com.hospital.dto.Response.ResponseBuilder;
import com.hospital.mapper.PrescriptionMapper;
import com.hospital.dto.UserDetailsResponse;
import com.hospital.dto.Response.MedicineResponse;

import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineService medicineService;
    private final UserService userService;
    
    public ResponseBuilder createPrescription(PrescriptionRequest request) {
        request.getMedications().forEach(med -> {
            MedicineResponse medicine = medicineService.getMedicineById(Long.parseLong(med.getMedicineId()));   
            
            med.setName(medicine.getName());
        });

        Prescription prescription = PrescriptionMapper.toEntity(request);
        prescription.setId(UUID.randomUUID().toString());
        prescription.setCreatedAt(LocalDateTime.now());
        prescription.setUpdatedAt(LocalDateTime.now());
        
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return ResponseBuilder.builder()
            .id(savedPrescription.getId())
            .doctorId(savedPrescription.getDoctorId())
            .patientId(savedPrescription.getPatientId())
            .reservationId(savedPrescription.getReservationId())
            .createdAt(savedPrescription.getCreatedAt())
            .updatedAt(savedPrescription.getUpdatedAt())
            .notes(savedPrescription.getNotes())
            .medications(savedPrescription.getMedications().stream()
                .map(PrescriptionMapper::toMedicationResponse)
                .collect(Collectors.toList()))
            .build();
    }
    
    public ResponseBuilder getPrescriptionById(String id) {
        Prescription prescription = prescriptionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Prescription not found"));
        return PrescriptionMapper.toDto(prescription);
    }
    
    public List<ResponseBuilder> getPrescriptionsByPatientId(String patientId) {
        return prescriptionRepository.findByPatientId(patientId).stream()
            .map(PrescriptionMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<ResponseBuilder> getPrescriptionsByPatientId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsResponse userResponse = userService.getCurrentUserDetails(userDetails);
        
        String patientId = userResponse.getId().toString();
        
        if (patientId == null) {
            throw new NotFoundException("Patient not found");
        }
        
        return prescriptionRepository.findByPatientId(patientId).stream()
            .map(PrescriptionMapper::toDto)
            .collect(Collectors.toList());
    }


    public List<ResponseBuilder> getPrescriptionsByDoctorId(String doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId).stream()
            .map(PrescriptionMapper::toDto)
            .collect(Collectors.toList());
    }
} 