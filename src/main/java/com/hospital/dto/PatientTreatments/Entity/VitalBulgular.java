package com.hospital.dto.PatientTreatments.Entity;

import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class VitalBulgular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String bloodPressure;
    private Integer pulse;
    private Double temperature;
    private Integer respiration;
    private Integer spO2;
    private Double height;
    private Double weight;
    private Double bmi;

    // {
    //     "doctorId": 1,
    //     "patientId": 1,
    //     "bloodPressure": "120/80",
    //     "pulse": 72,
    //     "temperature": 36.5,
    //     "respiration": 16,
    //     "spO2": 98,
    //     "height": 1.75,
    //     "weight": 70,
    //     "bmi": 23.5
    // }

    
}
