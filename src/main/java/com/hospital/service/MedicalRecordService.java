package com.hospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hospital.dto.MedicalRecordRequest;
import com.hospital.dto.UserDetailsResponse;
import com.hospital.dto.PatientTreatments.Entity.VitalBulgular;
import com.hospital.dto.PatientTreatments.request.VitalBulgularRequest;
import com.hospital.dto.Response.MedicalRecordResponse;
import com.hospital.entity.Doctor;
import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Patient;
import com.hospital.mapper.MedicalRecordMapper;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.VitalBulgularRepository;
import com.hospital.security.JwtUtil;
import com.hospital.repository.DoctorRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final VitalBulgularRepository vitalBulgularRepository;
    private final DoctorRepository doctorRepository;
    private final UserService userService;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository,
            VitalBulgularRepository vitalBulgularRepository, DoctorRepository doctorRepository, UserService userService) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
        this.vitalBulgularRepository = vitalBulgularRepository;
        this.doctorRepository = doctorRepository;
        this.userService = userService;
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

    public void createMedicalRecordByAppointmentId(VitalBulgularRequest vitalBulgularRequest) {
        UserDetails userDetails =  JwtUtil.getPrincipal();
        UserDetailsResponse userDetailsResponse = userService.getCurrentUserDetails(userDetails);
        Doctor doctor = doctorRepository.findById(userDetailsResponse.getId())
            .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Patient patient = patientRepository.findById(vitalBulgularRequest.getPatientId())
            .orElseThrow(() -> new IllegalArgumentException("Patient not found"));            
        VitalBulgular vitalBulgular = MedicalRecordMapper.toEntityByAppointment(vitalBulgularRequest, doctor, patient);
        vitalBulgularRepository.save(vitalBulgular);
    }

}
