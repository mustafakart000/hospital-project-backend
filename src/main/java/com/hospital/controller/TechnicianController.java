package com.hospital.controller;

import com.hospital.dto.ImagingResultDTO;
import com.hospital.dto.LabResultDTO;
import com.hospital.dto.TechnicianRequest;
import com.hospital.dto.TechnicianResponse;
import com.hospital.entity.ImagingRequest;
import com.hospital.entity.LabRequest;
import com.hospital.enums.Status;
import com.hospital.dto.RequestStatus;
import com.hospital.service.ImagingRequestService;
import com.hospital.service.LabRequestService;
import com.hospital.service.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/technicians")
@RequiredArgsConstructor
public class TechnicianController {

    private final TechnicianService technicianService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TechnicianResponse> createTechnician(@RequestBody TechnicianRequest request) {
        return new ResponseEntity<>(technicianService.createTechnician(request), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN')")
    public ResponseEntity<List<TechnicianResponse>> getAllTechnicians() {
        return ResponseEntity.ok(technicianService.getAllTechnicians());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN')")
    public ResponseEntity<TechnicianResponse> getTechnicianById(@PathVariable Long id) {
        return ResponseEntity.ok(technicianService.getTechnicianById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN')")
    public ResponseEntity<TechnicianResponse> updateTechnician(@PathVariable Long id, @RequestBody TechnicianRequest request) {
        return ResponseEntity.ok(technicianService.updateTechnician(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN')")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long id) {
        technicianService.deleteTechnician(id);
        return ResponseEntity.noContent().build();
    }

     private final LabRequestService labRequestService;
    private final ImagingRequestService imagingRequestService;

    // Laboratuvar istekleri için endpoint'ler
    @GetMapping("/lab-requests/all")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN','DOCTOR')")
    public ResponseEntity<List<LabRequest>> getAllLabRequests(
            @RequestParam(required = false) List<Status> status) {
        if (status == null || status.isEmpty()) {
            status = Arrays.asList(Status.PENDING, Status.COMPLETED);
        }
        return ResponseEntity.ok(labRequestService.getAllLabRequests(status));
    }

    @GetMapping("/lab-requests")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN','DOCTOR')")
    public ResponseEntity<List<LabRequest>> getPendingLabRequests() {
        return ResponseEntity.ok(labRequestService.getPendingRequests());
    }

    @PutMapping("/lab-requests/{requestId}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN','DOCTOR')")
    public ResponseEntity<LabRequest> completeLabRequest(
            @PathVariable Long requestId,
            @RequestBody LabResultDTO resultDTO) {
        return ResponseEntity.ok(labRequestService.completeLabRequest(requestId, resultDTO));
    }
    
    // Görüntüleme istekleri için endpoint'ler
    @GetMapping("/imaging-requests/all")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN','DOCTOR')")
    public ResponseEntity<List<ImagingRequest>> getAllImagingRequests(
            @RequestParam(required = false) List<RequestStatus> status) {
        if (status == null || status.isEmpty()) {
            status = Arrays.asList(RequestStatus.PENDING, RequestStatus.COMPLETED);
        }
        return ResponseEntity.ok(imagingRequestService.getAllImagingRequests(status));
    }

    @GetMapping("/imaging-requests")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN','DOCTOR')")
    public ResponseEntity<List<ImagingRequest>> getPendingImagingRequests() {
        return ResponseEntity.ok(imagingRequestService.getPendingRequests());
    }

    @PutMapping("/imaging-requests/{requestId}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN','DOCTOR')")
    public ResponseEntity<ImagingRequest> completeImagingRequest(
            @PathVariable Long requestId,
            @RequestBody ImagingResultDTO resultDTO) {
        return ResponseEntity.ok(imagingRequestService.completeImagingRequest(requestId, resultDTO));
    }

    @GetMapping("/imaging-requests/completed")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN','DOCTOR')")
    public ResponseEntity<List<ImagingRequest>> getCompletedImagingRequests() {
        return ResponseEntity.ok(imagingRequestService.getCompletedRequests());
    }

    @GetMapping("/imaging-requests/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIAN','DOCTOR','PATIENT')")
    public ResponseEntity<List<ImagingRequest>> getPatientImagingRequests(@PathVariable Long patientId) {
        return ResponseEntity.ok(imagingRequestService.getPatientImagingRequests(patientId));
    }

} 