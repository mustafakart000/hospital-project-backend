package com.hospital.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianResponse {
    private Long id;
    private String ad;
    private String soyad;
    private String username;
    private String email;
    private String tcKimlik;
    private String adres;
    private String kanGrubu;
    private String telefon;
    private String department;
    private LocalDate birthDate;
    private String specialization;
} 