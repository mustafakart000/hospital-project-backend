package com.hospital.dto.Response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBuilder {
    private String id;
    private String doctorId;
    private String patientId;
    private String reservationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notes;
    private List<PrescriptionMedicationResponse> medications;
} 