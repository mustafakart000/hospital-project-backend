package com.hospital.service;

import org.springframework.stereotype.Service;

import com.hospital.dto.MedicalRecordRequest;
import com.hospital.dto.Response.MedicalRecordResponse;
import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Patient;
import com.hospital.mapper.MedicalRecordMapper;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    public MedicalRecord createMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        Patient patient = patientRepository.findById(medicalRecordRequest.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalRecord medicalRecord = MedicalRecordMapper.toEntity(medicalRecordRequest, patient);
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecordResponse getMedicalRecordById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found"));
        return MedicalRecordMapper.toDto(medicalRecord);
    }

    public List<MedicalRecordResponse> getMedicalRecordsByPatientId(Long patientId) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientId(patientId);
        if (medicalRecords.isEmpty()) {
            throw new RuntimeException("No medical records found for the patient");
        }
        return medicalRecords.stream()
                             .map(MedicalRecordMapper::toDto)
                             .collect(Collectors.toList());
    }
}
