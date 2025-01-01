package com.hospital.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.dto.PatientTreatments.Entity.VitalBulgular;


public interface VitalBulgularRepository extends JpaRepository<VitalBulgular, Long> {
   
}       
