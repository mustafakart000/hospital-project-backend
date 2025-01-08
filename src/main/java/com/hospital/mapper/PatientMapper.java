package com.hospital.mapper;

import com.hospital.entity.Patient;
import com.hospital.dto.Response.PatientResponse;
import com.hospital.dto.PatientRequest;
import com.hospital.dto.RegisterRequest;
import com.hospital.dto.Response.MedicalRecordResponse;
import java.util.stream.Collectors;
import com.hospital.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PatientMapper {


  

    public static Patient mapToPatient(RegisterRequest request, PasswordEncoder passwordEncoder) {
        Patient patient = new Patient();
        patient.setTcKimlik(request.getTcKimlik());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setRole(Role.PATIENT.name());
        patient.setUsername(request.getUsername());
        patient.setAd(request.getAd());
        patient.setSoyad(request.getSoyad());
        patient.setEmail(request.getEmail());
        patient.setTelefon(request.getTelefon());
        patient.setAdres(request.getAdres());
        patient.setBirthDate(request.getBirthDate());
        patient.setKanGrubu(request.getKanGrubu());
        return patient;
    }


    public static PatientResponse mapToPatientResponse(Patient patient) {
        System.out.println("Patient: " + patient.getDoctors());
        return PatientResponse.builder()
            .id(patient.getId())
            .tcKimlik(patient.getTcKimlik())
            .ad(patient.getAd())
            .soyad(patient.getSoyad())
            .email(patient.getEmail())
            .telefon(patient.getTelefon())
            .username(patient.getUsername())
            .role(patient.getRole())
            .adres(patient.getAdres())
            .birthDate(patient.getBirthDate())
            .kanGrubu(patient.getKanGrubu())
            .doctors(patient.getDoctors())
            .reservations(patient.getReservations())
            .medicalRecords(patient.getMedicalHistory().stream()
                .map(medicalRecord -> MedicalRecordResponse.builder()
                    .id(medicalRecord.getId())
                    .category(medicalRecord.getCategory())
                    .date(medicalRecord.getDate())
                    .title(medicalRecord.getTitle())
                    .description(medicalRecord.getDescription())
                    .doctorNotes(medicalRecord.getDoctorNotes())
                    .additionalInfo(medicalRecord.getAdditionalInfo())
                    .attachments(medicalRecord.getAttachments())
                    .build())
                .collect(Collectors.toList()))
            .build();
            
    }

    public static void mapToPatient(Patient patient, PatientRequest request) {
        patient.setAd(request.getAd());
        patient.setSoyad(request.getSoyad());
        patient.setEmail(request.getEmail());
        patient.setTelefon(request.getTelefon());
        patient.setAdres(request.getAdres());
        patient.setBirthDate(request.getDogumTarihi());
        patient.setKanGrubu(request.getKanGrubu());
    }
}
