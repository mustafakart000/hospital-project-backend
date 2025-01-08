package com.hospital.repository;

import com.hospital.entity.ImagingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ImagingRequestRepository extends JpaRepository<ImagingRequest, Long> {
    List<ImagingRequest> findByPatientId(Long patientId);
    List<ImagingRequest> findByDoctorId(Long doctorId);
} 