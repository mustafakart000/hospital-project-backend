package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabRequestDTO {
    private String patientId;
    private String doctorId;
    private String reservationId;
    private String testPanel;  // Seçilen test paneli
    private String priority;          // NORMAL, URGENT
    private String fastingStatus;     // AC, TOK, FARKETMEZ
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;            // PENDING, COMPLETED, CANCELLED
} 