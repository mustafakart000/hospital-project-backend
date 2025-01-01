package com.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;


import com.hospital.dto.PatientTreatments.Entity.DiagnosisRequest;
import com.hospital.service.DiagnosisService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/doctor/diagnoses")
@RequiredArgsConstructor
@PreAuthorize("hasRole('DOCTOR')")
public class DiagnosisController {
    
    private final DiagnosisService diagnosisService;
    
    @PostMapping("/create")
    
    public ResponseEntity<DiagnosisRequest> createDiagnosis(@RequestBody DiagnosisRequest request) {
        
        return ResponseEntity.ok(diagnosisService.createDiagnosis(request));
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<DiagnosisRequest> getDiagnosis(@PathVariable Long id) {
        return ResponseEntity.ok(diagnosisService.getDiagnosis(id));
    }
    
    @GetMapping("/patient/get/{patientId}")
    public ResponseEntity<List<DiagnosisRequest>> getDiagnosesByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(diagnosisService.getDiagnosesByPatient(patientId));
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<DiagnosisRequest> updateDiagnosis(
            @PathVariable Long id,
            @RequestBody DiagnosisRequest request) {
        return ResponseEntity.ok(diagnosisService.updateDiagnosis(id, request));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
        return ResponseEntity.noContent().build();
    }
}
// localhost:8080/doctor/diagnoses/create
// {
//     "diagnosticInfo": {
//         "preliminaryDiagnosis": "string",
//         "finalDiagnosis": "string",
//         "diagnosticDetails": "string",
//         "icdCode": "string"
//     },
// }
// localhost:8080/doctor/diagnoses/get/1
// localhost:8080/doctor/diagnoses/patient/get/1
// localhost:8080/doctor/diagnoses/update/1
// localhost:8080/doctor/diagnoses/delete/1

