package com.hospital.service;

import org.springframework.stereotype.Service;

import com.hospital.repository.PatientRepository;
import com.hospital.dto.PatientRequest;
import com.hospital.dto.Response.PatientResponse;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.mapper.PatientMapper;
import com.hospital.entity.Patient;

@Service
public class PatientService {
    
    private final PatientRepository patientRepository;


    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return PatientMapper.mapToPatientResponse(patient); 
    }

    public void updatePatient(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        PatientMapper.mapToPatient(patient, request);
        patientRepository.save(patient);
    }

}