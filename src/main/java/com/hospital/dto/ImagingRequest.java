package com.hospital.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagingRequest {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long assignedTechnicianId;
    private String imagingType;
    private String bodyPart;
    private String notes;
    private String priority;
    private RequestStatus status;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 