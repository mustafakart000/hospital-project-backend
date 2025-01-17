package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hospital.service.ImagingRequestService;
import com.hospital.dto.ImagingRequestDTO;
import com.hospital.dto.ImagingRequestResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/imaging-requests")
@CrossOrigin
public class ImagingRequestController {
    private final ImagingRequestService imagingRequestService;

    public ImagingRequestController(ImagingRequestService imagingRequestService) {
        this.imagingRequestService = imagingRequestService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ImagingRequestResponseDTO> createRequest(@RequestBody ImagingRequestDTO request) {
        return ResponseEntity.ok(imagingRequestService.createRequest(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ImagingRequestResponseDTO> updateRequest(@PathVariable Long id, @RequestBody ImagingRequestDTO request) {
        return ResponseEntity.ok(imagingRequestService.updateRequest(id, request));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public ResponseEntity<List<ImagingRequestResponseDTO>> getPatientRequests(@PathVariable Long patientId) {
        return ResponseEntity.ok(imagingRequestService.getPatientRequests(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<ImagingRequestResponseDTO>> getDoctorRequests(@PathVariable Long doctorId) {
        return ResponseEntity.ok(imagingRequestService.getDoctorRequests(doctorId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        imagingRequestService.deleteRequest(id);
        return ResponseEntity.ok().build();
    }
} 