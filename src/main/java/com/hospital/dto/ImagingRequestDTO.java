package com.hospital.dto;

import lombok.Data;

@Data
public class ImagingRequestDTO {
    private String imagingType;
    private String priority;
    private String notes;
    private String bodyPart;
    private Long patientId;
    private Long doctorId;
} 