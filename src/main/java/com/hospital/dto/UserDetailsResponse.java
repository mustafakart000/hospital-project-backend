package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
    private String ad;
    private String soyad;
    private String token;
    // İhtiyaca göre diğer alanlar eklenebilir
} 