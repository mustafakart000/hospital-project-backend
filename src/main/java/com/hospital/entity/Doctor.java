package com.hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import com.hospital.dto.PatientTreatments.Entity.VitalBulgular;
import com.hospital.model.DoctorSpeciality;

import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {

    
    private String diplomaNo;
    

    @OneToMany(mappedBy = "doctor")
    private List<VitalBulgular> vitalBulgulars;

    private String unvan;

    @Enumerated(EnumType.STRING)
    private DoctorSpeciality uzmanlik;


    @ManyToMany(mappedBy = "doctors")
    private List<Patient> patients;
}
