package com.hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {
    private String uzmanlik;
    private String diplomaNo;
    private String unvan;

    @ManyToMany(mappedBy = "doctors")
    private List<Patient> patients;
}
