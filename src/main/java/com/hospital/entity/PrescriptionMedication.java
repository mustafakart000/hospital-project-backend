package com.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescription_medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionMedication {
    @Id
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;
    
    private String medicineId;
    private String name;
    private String dosage;
    @Column(name = "usage_instruction")
    private String usage;
    private String frequency;
    private Integer duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
} 