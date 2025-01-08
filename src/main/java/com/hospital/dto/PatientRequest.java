package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {
    private String ad;
    private String soyad;
    private String email;
    private String telefon;
    private String adres;
    private LocalDate dogumTarihi;
    private String kanGrubu;
    private String tcKimlik;
    private String password;
    private String username;
}
