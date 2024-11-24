package com.hospital.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientRegistrationDto {
    private String username;
    private String password;
    private String tc_no;
    private String telefon;
    private String adres;
    private LocalDate dogum_tarihi;
    private String kan_grubu;
} 