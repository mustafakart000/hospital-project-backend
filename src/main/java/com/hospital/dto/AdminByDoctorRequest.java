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
public class AdminByDoctorRequest {
    private String ad;
    private String soyad;
    private String username;
    private String tcKimlik;
    private String email;
    private String telefon;
    private String adres;
    private String diplomaNo;
    private String unvan;
    private String uzmanlik;
    private LocalDate birthDate;
    private String kanGrubu;
    private String password;
}
