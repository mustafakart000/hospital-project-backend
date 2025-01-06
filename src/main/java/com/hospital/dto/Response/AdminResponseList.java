package com.hospital.dto.Response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminResponseList {
    
    // Add necessary fields for admin response list
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;
    private String ad;
    private String soyad;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private String tcKimlik;
    private String kanGrubu;
    




 

}
