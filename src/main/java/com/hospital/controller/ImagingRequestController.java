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
import org.springframework.http.MediaType;
import java.util.Map;
import java.util.HashMap;
import java.util.Base64;
import com.hospital.dto.ImagingResultDTO;
import com.hospital.entity.ImagingRequest;

@RestController
@RequestMapping("/api/imaging-requests")
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
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN','TECHNICIAN')")
    public ResponseEntity<Map<String, Object>> getImagingRequestImage(
        @PathVariable Long patientId,
        @PathVariable Long imageId
    ) {
        ImagingRequestResponseDTO requestDetails = imagingRequestService.getPatientImageRequest(patientId, imageId);
        
        if (requestDetails == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "NOT_FOUND");
            error.put("message", "Görüntüleme isteği bulunamadı");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        byte[] imageBytes = imagingRequestService.getImageData(patientId, imageId);
        
        if (imageBytes == null || imageBytes.length == 0) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "NOT_FOUND");
            error.put("message", "Görüntü verisi henüz yüklenmemiş");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", imageId);
        response.put("patientId", patientId);
        response.put("imageData", base64Image);
        response.put("imagingType", requestDetails.getImagingType());
        response.put("status", requestDetails.getStatus());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}/images/{imageId}/raw")
    public ResponseEntity<byte[]> getImagingRequestImageRaw(
        @PathVariable Long patientId,
        @PathVariable Long imageId
    ) {
        byte[] imageBytes = imagingRequestService.getImageData(patientId, imageId);
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(imageBytes);
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

    @PostMapping("/patient/{patientId}/images/{imageId}/complete")
    @PreAuthorize("hasAnyRole('TECHNICIAN','DOCTOR','ADMIN','PATIENT')")
    public ResponseEntity<ImagingRequestResponseDTO> completeImagingRequest(
            @PathVariable Long patientId,
            @PathVariable Long imageId,
            @RequestBody ImagingResultDTO resultDTO) {
        
        try {
            // Önce isteğin var olduğunu ve hastaya ait olduğunu kontrol et
            ImagingRequestResponseDTO request = imagingRequestService.getPatientImageRequest(patientId, imageId);
            if (request == null) {
                return ResponseEntity.notFound().build();
            }
            
            // İsteği tamamla
            ImagingRequest completedRequest = imagingRequestService.completeImagingRequest(imageId, resultDTO);
            
            // Güncellenmiş isteği getir
            return ResponseEntity.ok(imagingRequestService.getPatientImageRequest(patientId, imageId));
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "BAD_REQUEST");
            error.put("message", "Geçersiz görüntü verisi formatı: " + e.getMessage());
            return ResponseEntity.badRequest().body(new ImagingRequestResponseDTO());
        }
    }
} 