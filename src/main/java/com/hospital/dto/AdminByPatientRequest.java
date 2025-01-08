package com.hospital.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminByPatientRequest {
    private String ad;
    private String soyad;
    private String email;
    private String telefon;
    private String adres;
    private LocalDate birthDate;
    private String kanGrubu;
    private String tcKimlik;
    private String password;
    private String username;
}
