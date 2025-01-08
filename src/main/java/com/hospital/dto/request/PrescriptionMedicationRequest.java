package com.hospital.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionMedicationRequest {
    private String medicineId;
    private String name;
    private String dosage;
    private String usage;
    private String frequency;
    private Integer duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
} 