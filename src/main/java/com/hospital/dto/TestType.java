package com.hospital.dto;

import com.hospital.entity.Patient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "test_type")
public class TestType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patientId;
    private String tamKanSayimi;
    // private String hormonTestleri;
    // private String biyokimya;
    // private String idrarTahlili;
    // private String koagülasyon;
    // private String sedimantasyon;
    // private String CRP;
    
    
    
}
