
package com.hospital.dto.PatientTreatments.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hospital.model.TreatmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisRequest {
    private DiagnosticInfo diagnosticInfo;
    private TreatmentPlan treatmentPlan;
    private Actions actions;
    private Metadata metadata;
    private long reservationId;
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiagnosticInfo {
        private String preliminaryDiagnosis;
        private String finalDiagnosis;
        private String diagnosticDetails;
        private String icdCode;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreatmentPlan {
        private TreatmentType treatmentType;
        private String treatmentDetails;
        private LocalDate followUpDate;
        
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Actions {
        private boolean saveAndExit;
        private boolean requestConsultation;
        private boolean createAppointment;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private String patientId;
        private String doctorId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

   
}