package com.hospital.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImagingResultDTO {
    private Long requestId;
    private String imageUrl;
    private byte[] imageData;
    private String findings;
    private String notes;
    private LocalDateTime completedAt;
    private String technicianId;
}
