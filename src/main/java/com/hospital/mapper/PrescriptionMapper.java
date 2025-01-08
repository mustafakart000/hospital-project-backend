package com.hospital.mapper;

import com.hospital.dto.request.PrescriptionRequest;
import com.hospital.dto.request.PrescriptionMedicationRequest;
import com.hospital.dto.Response.ResponseBuilder;
import com.hospital.dto.Response.PrescriptionMedicationResponse;
import com.hospital.entity.Prescription;
import com.hospital.entity.PrescriptionMedication;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.UUID;
import java.util.List;

@Component
public class PrescriptionMapper {
    
    public static Prescription toEntity(PrescriptionRequest request) {
        Prescription prescription = Prescription.builder()
            .doctorId(request.getDoctorId())
            .patientId(request.getPatientId())
            .reservationId(request.getReservationId())
            .notes(request.getNotes())
            .build();
            
        List<PrescriptionMedication> medications = request.getMedications().stream()
            .map(med -> {
                PrescriptionMedication medication = toMedicationEntity(med);
                medication.setPrescription(prescription);
                return medication;
            })
            .collect(Collectors.toList());
            
        prescription.setMedications(medications);
        return prescription;
    }

    public static PrescriptionMedication toMedicationEntity(PrescriptionMedicationRequest request) {
        return PrescriptionMedication.builder()
            .id(UUID.randomUUID().toString())
            .medicineId(request.getMedicineId())
            .name(request.getName())
            .dosage(request.getDosage())
            .usage(request.getUsage())
            .frequency(request.getFrequency())
            .duration(request.getDuration())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .build();
    }

    public static ResponseBuilder toDto(Prescription prescription) {
        return ResponseBuilder.builder()
            .id(prescription.getId())
            .doctorId(prescription.getDoctorId())
            .patientId(prescription.getPatientId())
            .reservationId(prescription.getReservationId())
            .createdAt(prescription.getCreatedAt())
            .updatedAt(prescription.getUpdatedAt())
            .notes(prescription.getNotes())
            .medications(prescription.getMedications().stream()
                .map(PrescriptionMapper::toMedicationResponse)
                .collect(Collectors.toList()))
            .build();
    }

    public static PrescriptionMedicationResponse toMedicationResponse(PrescriptionMedication medication) {
        return PrescriptionMedicationResponse.builder()
            .id(medication.getId())
            .medicineId(medication.getMedicineId())
            .name(medication.getName())
            .dosage(medication.getDosage())
            .usage(medication.getUsage())
            .frequency(medication.getFrequency())
            .duration(medication.getDuration())
            .startDate(medication.getStartDate())
            .endDate(medication.getEndDate())
            .build();
    }
} 