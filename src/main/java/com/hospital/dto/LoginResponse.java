package com.hospital.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Kullanıcı giriş yanıtını temsil eden DTO sınıfı
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String username; // Kullanıcı adı
    private String role; // Kullanıcı rolü
    private String token; // JWT token
    private String message; // Yanıt mesajı
}