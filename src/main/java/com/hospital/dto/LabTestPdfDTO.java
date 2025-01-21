package com.hospital.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabTestPdfDTO {
    private String testType;
    private String pdfUrl;
    private byte[] pdfData;
} 