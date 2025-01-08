package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.dto.PatientTreatments.Entity.DiagnosisEntity;

public interface DiagnosisRepository extends JpaRepository<DiagnosisEntity, Long> {
    @Query("SELECT d FROM DiagnosisEntity d WHERE d.patientId = :patientId")
    List<DiagnosisEntity> findByPatientId(@Param("patientId") String patientId);
}



