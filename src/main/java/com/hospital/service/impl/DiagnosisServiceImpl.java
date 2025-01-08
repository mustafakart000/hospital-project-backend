package com.hospital.service.impl;

import org.springframework.stereotype.Service;

import com.hospital.service.DiagnosisService;
import com.hospital.repository.DiagnosisRepository;
import com.hospital.mapper.DiagnosisMapper;
import com.hospital.dto.PatientTreatments.Entity.DiagnosisEntity;
import com.hospital.dto.PatientTreatments.request.DiagnosisRequest;
import com.hospital.entity.Reservations;
import com.hospital.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.hospital.repository.ReservationsRepository;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final DiagnosisMapper diagnosisMapper;
    private final ReservationsRepository reservationsRepository;
    @Override
    public DiagnosisRequest createDiagnosis(DiagnosisRequest request) {
        // Set creation time
        request.getMetadata().setCreatedAt(LocalDateTime.now());
        request.getMetadata().setUpdatedAt(LocalDateTime.now());
        Reservations reservation = reservationsRepository.findById(request.getReservationId())
        .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + request.getReservationId()));
        // Convert to entity
        DiagnosisEntity entity = diagnosisMapper.toEntity(request, reservation);
        
        // Save to database
        DiagnosisEntity savedEntity = diagnosisRepository.save(entity);
        
        // Rezervasyonu güncelle
        reservation.setDiagnosis(savedEntity);
        reservation.setTreatmentDate(LocalDateTime.now());
        reservation.setTreated(true);
        reservationsRepository.save(reservation);
        
        // Handle consultation request if needed
        if (request.getActions().isRequestConsultation()) {
            handleConsultationRequest(savedEntity);
        }
        
        // Handle appointment creation if needed
        if (request.getActions().isCreateAppointment()) {
            createFollowUpAppointment(savedEntity);
        }
        
        // Convert back to DTO and return
        return diagnosisMapper.toDto(savedEntity);
    }

    @Override
    public DiagnosisRequest getDiagnosis(Long id) {
        DiagnosisEntity entity = diagnosisRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Diagnosis not found with id: " + id));
        return diagnosisMapper.toDto(entity);
    }

    @Override
    public List<DiagnosisRequest> getDiagnosesByPatient(String patientId) {
        List<DiagnosisEntity> entities = diagnosisRepository.findByPatientId(patientId);
        return entities.stream()
                .map(diagnosisMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DiagnosisRequest updateDiagnosis(Long id, DiagnosisRequest request) {
        // Check if exists
        DiagnosisEntity existingEntity = diagnosisRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Diagnosis not found with id: " + id));
        
        // Update metadata
        request.getMetadata().setCreatedAt(existingEntity.getCreatedAt());
        request.getMetadata().setUpdatedAt(LocalDateTime.now());
        Reservations reservation = reservationsRepository.findById(request.getReservationId())   
        .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + request.getReservationId()));
        // Map and save
        DiagnosisEntity entityToUpdate = diagnosisMapper.toEntity(request, reservation);
        entityToUpdate.setId(id);
        DiagnosisEntity updatedEntity = diagnosisRepository.save(entityToUpdate);
        
        return diagnosisMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteDiagnosis(Long id) {
        if (!diagnosisRepository.existsById(id)) {
            throw new NotFoundException("Diagnosis not found with id: " + id);
        }
        diagnosisRepository.deleteById(id);
    }

    private void handleConsultationRequest(DiagnosisEntity diagnosis) {
        // Implement consultation request logic
        // This could involve creating a consultation request record
        // and notifying relevant doctors
    }

    private void createFollowUpAppointment(DiagnosisEntity diagnosis) {
        // Implement appointment creation logic
        // This could involve integration with an appointment scheduling system
    }

    
}
