package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianRequest {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String department;
    private String specialization;
    private LocalDate birthDate;
    private String tcKimlik;
    private String adres;
    private String kanGrubu;
} 