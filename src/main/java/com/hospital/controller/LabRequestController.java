package com.hospital.controller;

import com.hospital.dto.LabRequestDTO;
import com.hospital.entity.LabRequest;
import com.hospital.entity.LabTestPdf;
import com.hospital.enums.Status;
import com.hospital.service.LabRequestService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64;

@RestController
@RequestMapping("/api/lab-requests")
@CrossOrigin
@RequiredArgsConstructor
public class LabRequestController {
    private final LabRequestService labRequestService;

    @PostMapping("/create")
    public ResponseEntity<LabRequest> createLabRequest(@RequestBody LabRequestDTO dto) {
        LabRequest created = labRequestService.createLabRequest(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<LabRequest>> getPatientLabRequests(@PathVariable Long patientId) {
        List<LabRequest> requests = labRequestService.getLabRequestsByPatient(patientId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<LabRequest>> getDoctorLabRequests(@PathVariable String doctorId) {
        List<LabRequest> requests = labRequestService.getLabRequestsByDoctor(doctorId);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<LabRequest> updateStatus(
            @PathVariable Long requestId,
            @RequestParam Status status) {
        LabRequest updated = labRequestService.updateLabRequestStatus(requestId, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteLabRequest(@PathVariable Long requestId) {
        labRequestService.deleteLabRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'TECHNICIAN')")
    public ResponseEntity<List<LabRequest>> getAllPatientLabRequests(
            @PathVariable Long patientId,
            @RequestParam(required = false) List<Status> status) {
        if (status == null || status.isEmpty()) {
            status = Arrays.asList(Status.PENDING, Status.COMPLETED);
        }
        return ResponseEntity.ok(labRequestService.getAllPatientLabRequests(patientId, status));
    }

    @GetMapping("/patient/{patientId}/pdfs")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'TECHNICIAN','PATIENT')")
    public ResponseEntity<List<LabTestPdf>> getLabTestPdfsByPatientId(
            @PathVariable Long patientId,
            @RequestParam(required = false) List<Status> status) {
        if (status == null || status.isEmpty()) {
            status = Arrays.asList(Status.PENDING, Status.COMPLETED);
        }
        return ResponseEntity.ok(labRequestService.getLabTestPdfsByPatientId(patientId, status));
    }

    @GetMapping("/patient/{patientId}/pdfs/{pdfId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'TECHNICIAN','PATIENT')")
    public ResponseEntity<Map<String, String>> getLabTestPdfData(
            @PathVariable Long patientId,
            @PathVariable Long pdfId) {
        byte[] pdfData = labRequestService.getLabTestPdfDataById(pdfId, patientId);
        String base64Data = Base64.getEncoder().encodeToString(pdfData);
        
        Map<String, String> response = new HashMap<>();
        response.put("pdfData", base64Data);
        response.put("fileName", "lab-result.pdf");
        
        return ResponseEntity.ok(response);
    }
} 