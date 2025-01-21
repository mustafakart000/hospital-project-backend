package com.hospital.repository;

import com.hospital.dto.RequestStatus;
import com.hospital.entity.LabRequest;
import com.hospital.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.hospital.dto.Priority;

@Repository
public interface LabRequestRepository extends JpaRepository<LabRequest, Long> {
    List<LabRequest> findByPatient_Id(Long patientId);
    List<LabRequest> findByDoctorId(String doctorId);
    List<LabRequest> findByStatus(Status status);
    Optional<LabRequest> findByIdAndPatient_Id(Long id, Long patientId);

    List<LabRequest> findByStatus(RequestStatus status);
    List<LabRequest> findByTechnicianId(String technicianId);
    List<LabRequest> findByStatusAndPriority(RequestStatus status, Priority priority);
    
    @Query("SELECT lr FROM LabRequest lr WHERE lr.status = :status " +
           "AND lr.createdAt BETWEEN :startDate AND :endDate")
    List<LabRequest> findByStatusAndDateRange(
        @Param("status") RequestStatus status,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
} 