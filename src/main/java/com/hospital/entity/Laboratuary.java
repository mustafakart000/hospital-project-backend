package com.hospital.entity;

import java.util.List;

import com.hospital.model.Laboratuvar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Laboratuary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String displayName;

    
     @Enumerated(EnumType.STRING)
     @Column(unique = true, nullable = false)
    private Laboratuvar laboratuvar;
    
}
