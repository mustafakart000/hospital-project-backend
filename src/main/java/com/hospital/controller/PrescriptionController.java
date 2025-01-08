package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.service.PrescriptionService;
import com.hospital.dto.request.PrescriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import com.hospital.dto.Response.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionService prescriptionService;
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ResponseBuilder> createPrescription(@RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(prescriptionService.createPrescription(request));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<ResponseBuilder> getPrescription(@PathVariable String id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }
    
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<List<ResponseBuilder>> getPatientPrescriptions(@PathVariable String patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId(patientId));
    }
    
    // patient reçetelerini getirmek için
    @GetMapping("/patient")
    @PreAuthorize("hasAnyRole('PATIENT')")
    public ResponseEntity<List<ResponseBuilder>> getPatientPrescriptions() {

        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId());
    }



@GetMapping("/doctor/{doctorId}")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<List<ResponseBuilder>> getPrescriptionsByDoctorId(@PathVariable String doctorId) {
    return ResponseEntity.ok(prescriptionService.getPrescriptionsByDoctorId(doctorId));
}

} 