package com.hospital.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabResultDTO {
    private Long requestId;
    private String results;
    private String notes;
    private LocalDateTime completedAt;
    private String technicianId;
    private List<LabTestPdfDTO> testPdfs;
    private String resultData;
}