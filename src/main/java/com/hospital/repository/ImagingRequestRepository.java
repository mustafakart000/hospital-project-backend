package com.hospital.repository;

import com.hospital.dto.RequestStatus;
import com.hospital.entity.ImagingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hospital.dto.Priority;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ImagingRequestRepository extends JpaRepository<ImagingRequest, Long> {
    List<ImagingRequest> findByPatientId(Long patientId);
    List<ImagingRequest> findByDoctorId(Long doctorId);

    List<ImagingRequest> findByStatus(RequestStatus status);
    List<ImagingRequest> findByTechnicianId(Long technicianId);
    List<ImagingRequest> findByImagingType(String imagingType);
    List<ImagingRequest> findByStatusAndPriority(RequestStatus status, Priority priority);
    
    @Query("SELECT ir FROM ImagingRequest ir WHERE ir.status = :status " +
           "AND ir.createdAt BETWEEN :startDate AND :endDate")
    List<ImagingRequest> findByStatusAndDateRange(
        @Param("status") RequestStatus status,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
} 