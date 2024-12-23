package com.hospital.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Entity
@Data
@Table(name = "medical_records")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private LocalDate date;
    private String title;
    private String description;
    private String doctorNotes;

    // Ek bilgiler ve ekler için alanlar
    private String additionalInfo; // JSON formatında saklanabilir
    private String attachments; // JSON formatında saklanabilir

    // Yeni eklenen alan
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}