package com.hospital.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.hospital.model.DoctorSpeciality;

@Entity
@Table(name = "medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String description;
    private String manufacturer;
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DoctorSpeciality> relatedSpecialties;
    
    private Integer dosage;
    private String insuranceCoverage;
    private Integer mg;
    private String coverage;
    private boolean isPrescriptionRequired;
} 