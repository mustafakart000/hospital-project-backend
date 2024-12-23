package com.hospital.dto;

import lombok.Data;

@Data
public class MedicalRecordRequest {
    
    private Long id;
    private String additionalInfo;
    private String attachments;
    private String category;
    private String date;
    private String description;
    private String doctorNotes;
    private String title;
    private Long patientId;

}
