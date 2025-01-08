package com.hospital.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequest {
    private String doctorId;
    private String patientId;
    private String reservationId;
    private String notes;
    private List<PrescriptionMedicationRequest> medications;
} 