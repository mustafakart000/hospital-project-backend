package com.hospital.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lab_test_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTestImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_request_id")
    private LabRequest labRequest;
    
    @Column(name = "test_type")
    private String testType;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;
}
