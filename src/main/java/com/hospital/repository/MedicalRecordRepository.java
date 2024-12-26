package com.hospital.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientId(Long patientId);
}
