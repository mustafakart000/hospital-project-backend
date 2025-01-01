package com.hospital.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "blood_tests")
@AllArgsConstructor
@NoArgsConstructor
public class BloodTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "laboratory_id", nullable = false)
    private Laboratuary laboratory;

    // Hemogram (CBC) Değerleri
    @Column(name = "wbc")
    private Double wbc; // Beyaz kan hücresi (10^3/µL)

    @Column(name = "rbc")
    private Double rbc; // Kırmızı kan hücresi (10^6/µL)

    @Column(name = "hemoglobin")
    private Double hemoglobin; // g/dL

    @Column(name = "hematocrit")
    private Double hematocrit; // %

    @Column(name = "mcv")
    private Double mcv; // fL

    @Column(name = "mch")
    private Double mch; // pg

    @Column(name = "mchc")
    private Double mchc; // g/dL

    @Column(name = "platelets")
    private Double platelets; // 10^3/µL

    @Column(name = "neutrophils_percent")
    private Double neutrophilsPercent; // %

    @Column(name = "lymphocytes_percent")
    private Double lymphocytesPercent; // %

    @Column(name = "monocytes_percent")
    private Double monocytesPercent; // %

    @Column(name = "eosinophils_percent")
    private Double eosinophilsPercent; // %

    @Column(name = "basophils_percent")
    private Double basophilsPercent; // %

    // Biyokimya Değerleri
    @Column(name = "glucose")
    private Double glucose; // mg/dL

    @Column(name = "urea")
    private Double urea; // mg/dL

    @Column(name = "creatinine")
    private Double creatinine; // mg/dL

    @Column(name = "uric_acid")
    private Double uricAcid; // mg/dL

    @Column(name = "total_protein")
    private Double totalProtein; // g/dL

    @Column(name = "albumin")
    private Double albumin; // g/dL

    @Column(name = "total_bilirubin")
    private Double totalBilirubin; // mg/dL

    @Column(name = "direct_bilirubin")
    private Double directBilirubin; // mg/dL

    @Column(name = "ast")
    private Double ast; // U/L

    @Column(name = "alt")
    private Double alt; // U/L

    @Column(name = "alp")
    private Double alp; // U/L

    @Column(name = "ggtp")
    private Double ggtp; // U/L

    @Column(name = "ldh")
    private Double ldh; // U/L

    @Column(name = "sodium")
    private Double sodium; // mEq/L

    @Column(name = "potassium")
    private Double potassium; // mEq/L

    @Column(name = "chloride")
    private Double chloride; // mEq/L

    @Column(name = "calcium")
    private Double calcium; // mg/dL

    // Lipid Profili
    @Column(name = "total_cholesterol")
    private Double totalCholesterol; // mg/dL

    @Column(name = "hdl_cholesterol")
    private Double hdlCholesterol; // mg/dL

    @Column(name = "ldl_cholesterol")
    private Double ldlCholesterol; // mg/dL

    @Column(name = "triglycerides")
    private Double triglycerides; // mg/dL

    // Test Meta Bilgileri
    @Column(name = "test_date", nullable = false)
    private LocalDateTime testDate;

    @Column(name = "laboratory_name")
    private String laboratoryName;

    @Column(name = "doctor_notes")
    private String doctorNotes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}