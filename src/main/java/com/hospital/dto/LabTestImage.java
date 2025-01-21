package com.hospital.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabTestImage {
    private String testType;
    private String imageUrl;
    private byte[] imageData;
} 