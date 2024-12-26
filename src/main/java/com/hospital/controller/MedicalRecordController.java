package com.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.MedicalRecordRequest;
import com.hospital.dto.Response.MedicalRecordResponse;
import com.hospital.entity.MedicalRecord;
import com.hospital.mapper.MedicalRecordMapper;
import com.hospital.service.MedicalRecordService;


@RestController
@RequestMapping("/medical-record")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }
    
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @PostMapping("/create")
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(@RequestBody MedicalRecordRequest medicalRecordRequest){
        
        MedicalRecord savedMedicalRecord = medicalRecordService.createMedicalRecord(medicalRecordRequest);
        MedicalRecordResponse response = MedicalRecordMapper.toDto(savedMedicalRecord);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @GetMapping("/get/{id}")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecordById(@PathVariable Long id){
        MedicalRecordResponse response = medicalRecordService.getMedicalRecordById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordResponse>> getMedicalRecordsByPatientId(@PathVariable Long patientId) {
        List<MedicalRecordResponse> responses = medicalRecordService.getMedicalRecordsByPatientId(patientId);
        return ResponseEntity.ok(responses);
    }


}
