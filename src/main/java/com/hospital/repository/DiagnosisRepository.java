package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.dto.PatientTreatments.Entity.DiagnosisEntity;


@Repository
public interface DiagnosisRepository extends JpaRepository<DiagnosisEntity, Long> {
    List<DiagnosisEntity> findByPatientId(String patientId);
    
}



