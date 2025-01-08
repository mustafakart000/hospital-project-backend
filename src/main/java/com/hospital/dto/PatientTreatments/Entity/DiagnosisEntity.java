package com.hospital.dto.PatientTreatments.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.hospital.model.TreatmentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.hospital.entity.Reservations;

import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "diagnoses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservations reservation;
    
    private String preliminaryDiagnosis;
    private String finalDiagnosis;
    private String diagnosticDetails;
    private String icdCode;
    
    @Enumerated(EnumType.STRING)
    private TreatmentType treatmentType;
    private String treatmentDetails;
    private LocalDate followUpDate;
   


    private String patientId;
    private String doctorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

