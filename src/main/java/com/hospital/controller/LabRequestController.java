package com.hospital.controller;

import com.hospital.dto.LabRequestDTO;
import com.hospital.entity.LabRequest;
import com.hospital.enums.Status;
import com.hospital.service.LabRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab-requests")
@CrossOrigin
public class LabRequestController {
    private final LabRequestService labRequestService;

    public LabRequestController(LabRequestService labRequestService) {
        this.labRequestService = labRequestService;
    }

    @PostMapping("/create")
    public ResponseEntity<LabRequest> createLabRequest(@RequestBody LabRequestDTO dto) {
        LabRequest created = labRequestService.createLabRequest(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<LabRequest>> getPatientLabRequests(@PathVariable String patientId) {
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
} 