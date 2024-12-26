package com.hospital.dto.Response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ReservationResponse {
    private Long id;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private String status;
    private String speciality;
    private Long doctorId;
    private String doctorName;
    private String doctorSurname;
    private Long patientId;
    private String patientName;
    private String patientSurname;
}
