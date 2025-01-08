package com.hospital.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagingResultRequest {
    private Long requestId;
    private Long technicianId;
    private String notes;
    private String imagePath;
} 