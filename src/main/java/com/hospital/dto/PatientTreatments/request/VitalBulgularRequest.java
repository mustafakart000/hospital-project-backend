package com.hospital.dto.PatientTreatments.request;

import lombok.Data;

@Data
public class VitalBulgularRequest {
    
    
    private Long patientId;
    private String bloodPressure;
    private Integer pulse;
    private Double temperature;
    private Integer respiration;
    private Integer spO2;
    private Double height;
    private Double weight;
    private Double bmi;
}


//json formatı
// {
//     "appointmentId": "string",
//     "bloodPressure": "120/80",
//     "pulse": 72,
//     "temperature": 36.5,
//     "respiration": 16,
//     "spO2": 98
//     "height": 1.75,
//     "weight": 70,
//     "bmi": 23.5
// }
