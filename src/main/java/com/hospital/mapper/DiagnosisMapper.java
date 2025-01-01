package com.hospital.mapper;

import org.springframework.stereotype.Component;

import com.hospital.dto.PatientTreatments.Entity.DiagnosisRequest;
import com.hospital.entity.DiagnosisEntity;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class DiagnosisMapper {
    
    public DiagnosisEntity toEntity(DiagnosisRequest request) {
        if (request == null) {
            return null;
        }

        return DiagnosisEntity.builder()
            .preliminaryDiagnosis(request.getDiagnosticInfo().getPreliminaryDiagnosis())
            .finalDiagnosis(request.getDiagnosticInfo().getFinalDiagnosis())
            .diagnosticDetails(request.getDiagnosticInfo().getDiagnosticDetails())
            .icdCode(request.getDiagnosticInfo().getIcdCode())
            .treatmentType(request.getTreatmentPlan().getTreatmentType())
            .treatmentDetails(request.getTreatmentPlan().getTreatmentDetails())
            .followUpDate(request.getTreatmentPlan().getFollowUpDate())
            .patientId(request.getMetadata().getPatientId())
            .doctorId(request.getMetadata().getDoctorId())
            .createdAt(request.getMetadata().getCreatedAt())
            .updatedAt(request.getMetadata().getUpdatedAt())
            .build();
            //postman: http://localhost:8080/doctor/diagnoses/create
            //{diagnosticInfo: {preliminaryDiagnosis: "string", finalDiagnosis: "string", diagnosticDetails: "string", icdCode: "string"}, treatmentPlan: {treatmentType: "string", treatmentDetails: "string", followUpDate: "string"}}
    }
    


    public DiagnosisRequest toDto(DiagnosisEntity entity) {
        if (entity == null) {
            return null;
        }

        return DiagnosisRequest.builder()
            .diagnosticInfo(DiagnosisRequest.DiagnosticInfo.builder()
                .preliminaryDiagnosis(entity.getPreliminaryDiagnosis())
                .finalDiagnosis(entity.getFinalDiagnosis())
                .diagnosticDetails(entity.getDiagnosticDetails())
                .icdCode(entity.getIcdCode())
                .build())
            .treatmentPlan(DiagnosisRequest.TreatmentPlan.builder()
                .treatmentType(entity.getTreatmentType())
                .treatmentDetails(entity.getTreatmentDetails())
                .followUpDate(entity.getFollowUpDate())
                .build())
            .metadata(DiagnosisRequest.Metadata.builder()
                .patientId(entity.getPatientId())
                .doctorId(entity.getDoctorId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build())
            .actions(DiagnosisRequest.Actions.builder()
                .saveAndExit(false)
                .requestConsultation(false)
                .createAppointment(false)
                .build())
            .build();
    }
}
