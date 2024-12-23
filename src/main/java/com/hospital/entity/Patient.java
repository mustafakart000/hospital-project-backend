package com.hospital.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "patients")
public class Patient extends User {

    private LocalDate dogum_tarihi;

    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalHistory; // MedicalRecord sınıfının listesi

    @ManyToMany
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Reservations> reservations;
}