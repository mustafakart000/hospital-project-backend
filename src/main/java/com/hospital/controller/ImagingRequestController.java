package com.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hospital.service.ImagingRequestService;
import com.hospital.dto.ImagingRequestDTO;
import com.hospital.dto.ImagingRequestResponseDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import java.net.URI;

@RestController
@RequestMapping("/imaging-requests")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Location")
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

    @GetMapping("/patient/{patientId}/images/{imageId}")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public ResponseEntity<ImagingRequestResponseDTO> getPatientImageRequest(
            @PathVariable Long patientId,
            @PathVariable Long imageId) {
        return ResponseEntity.ok(imagingRequestService.getPatientImageRequest(patientId, imageId));
    }

    @GetMapping("/patient/{patientId}/images/{imageId}/data")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN','TECHNICIAN')")
    public ResponseEntity<Object> getPatientImageData(
            @PathVariable Long patientId,
            @PathVariable Long imageId) {
        ImagingRequestResponseDTO response = imagingRequestService.getPatientImageRequest(patientId, imageId);
        if (response.getImagingUrl() != null && !response.getImagingUrl().isEmpty()) {
            // URL'i redirect olarak dön
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(response.getImagingUrl()))
                    .build();
        }
        return ResponseEntity.notFound().build();
    }
} 