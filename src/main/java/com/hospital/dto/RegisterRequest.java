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
public class RegisterRequest {
    private String username;   // Kullanıcı adı
    private String password;   // Kullanıcı şifresi
    private String ad;         // Kullanıcının adı
    private String soyad;      // Kullanıcının soyadı
    private String email;      // Kullanıcının e-posta adresi
    private String telefon;    // Kullanıcının telefon numarası
    private String adres;      // Kullanıcının adres bilgisi
    private LocalDate birthDate; // Kullanıcının doğum tarihi
    private String kanGrubu;   // Kullanıcının kan grubu
}
