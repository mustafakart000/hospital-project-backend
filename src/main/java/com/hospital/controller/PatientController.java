package com.hospital.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.PatientRequest;
import com.hospital.dto.Response.PatientResponse;
import com.hospital.service.PatientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/patient")
public class PatientController {
    
    
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


     //localhost:8080/patient/get/1
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable Long id) {
        PatientResponse response = patientService.getPatientById(id);
        return ResponseEntity.ok(response);
    }

    // /put/patient/1
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<String> updatePatient(@PathVariable Long id, @RequestBody PatientRequest request) {
        patientService.updatePatient(id, request);
        return ResponseEntity.ok("Updated successfully");
    }

   
 



}