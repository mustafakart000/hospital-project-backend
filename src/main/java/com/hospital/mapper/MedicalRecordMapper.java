package com.hospital.mapper;

import java.time.LocalDate;

import com.hospital.dto.MedicalRecordRequest;
import com.hospital.dto.PatientTreatments.Entity.VitalBulgular;
import com.hospital.dto.PatientTreatments.request.VitalBulgularRequest;
import com.hospital.dto.Response.MedicalRecordResponse;
import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Patient;
import com.hospital.entity.Doctor;

public class MedicalRecordMapper {



    public static MedicalRecord toEntity(MedicalRecordRequest request, Patient patient) {
        return MedicalRecord.builder()
                .id(request.getId())
                .category(request.getCategory())
                .date(LocalDate.parse(request.getDate()))
                .title(request.getTitle())
                .description(request.getDescription())
                .doctorNotes(request.getDoctorNotes())
                .additionalInfo(request.getAdditionalInfo())
                .attachments(request.getAttachments())
                .patient(patient)
                .build();
    }

    public static MedicalRecordResponse toDto(MedicalRecord medicalRecord) {
        MedicalRecordResponse response = new MedicalRecordResponse();
        response.setId(medicalRecord.getId());
        response.setCategory(medicalRecord.getCategory());
        response.setDate(medicalRecord.getDate());
        response.setTitle(medicalRecord.getTitle());
        response.setDescription(medicalRecord.getDescription());
        response.setDoctorNotes(medicalRecord.getDoctorNotes());
        response.setAdditionalInfo(medicalRecord.getAdditionalInfo());
        response.setAttachments(medicalRecord.getAttachments());
        response.setPatientId(medicalRecord.getPatient().getId());
        return response;
    }

    public static VitalBulgular toEntityByAppointment(VitalBulgularRequest appointmentRequest, Doctor doctor, Patient patient) {
        
        return VitalBulgular.builder()
                .doctor(doctor)
                .patient(patient)
                .bloodPressure(appointmentRequest.getBloodPressure())
                .pulse(appointmentRequest.getPulse())
                .temperature(appointmentRequest.getTemperature())
                .respiration(appointmentRequest.getRespiration())
                .spO2(appointmentRequest.getSpO2())
                .height(appointmentRequest.getHeight())
                .weight(appointmentRequest.getWeight())
                .bmi(appointmentRequest.getBmi())
                .build();
    }
}
