package com.hospital.repository;

import com.hospital.entity.LabRequest;
import com.hospital.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabRequestRepository extends JpaRepository<LabRequest, Long> {
    List<LabRequest> findByPatientId(String patientId);
    List<LabRequest> findByDoctorId(String doctorId);
    List<LabRequest> findByStatus(Status status);
    Optional<LabRequest> findByIdAndPatientId(Long id, String patientId);
} 