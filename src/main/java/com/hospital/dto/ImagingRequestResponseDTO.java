package com.hospital.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ImagingRequestResponseDTO {
    private Long id;
    private String imagingType;
    private String imagingUrl;
    private byte[] imageData;
    private String findings;
    private String priority;
    private String notes;
    private String bodyPart;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime createdAt;
} 