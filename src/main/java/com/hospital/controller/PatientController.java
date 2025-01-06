package com.hospital.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.dto.AdminByPatientRequest;
import com.hospital.dto.PatientRequest;
import com.hospital.dto.Response.PatientResponse;
import com.hospital.service.PatientService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/patient")
public class PatientController {
    
    
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


     //localhost:8080/patient/get/1
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable Long id) {
        PatientResponse response = patientService.getPatientById(id);
        return ResponseEntity.ok(response);
    }

    // /put/patient/1
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'ADMIN')")
    public ResponseEntity<String> updatePatient(@PathVariable Long id, @RequestBody PatientRequest request) {
        patientService.updatePatient(id, request);
        return ResponseEntity.ok("Updated successfully");
    }

    // /admin/update/1
    @PutMapping("/admin/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updatePatientAdmin(@PathVariable Long id, @RequestBody AdminByPatientRequest request) {
        patientService.updateByAdminPatient(id, request);
        return ResponseEntity.ok("Updated successfully");
    }

    
    // /patient/delete/1
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // /patient/all
    @GetMapping("/getall")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PatientResponse>> getAllPatients(
            @RequestParam(required = false) String searchTerm,
            Pageable pageable) {
        Page<PatientResponse> patients = patientService.getAllPatients(searchTerm, pageable);
        return ResponseEntity.ok(patients);
    }



}