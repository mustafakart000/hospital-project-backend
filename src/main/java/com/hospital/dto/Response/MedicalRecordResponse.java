package com.hospital.dto.Response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordResponse {
    private Long id;
    private String category;
    private LocalDate date;
    private String title;
    private String description;
    private String doctorNotes;
    private String additionalInfo;
    private String attachments;
    private Long patientId;
}

