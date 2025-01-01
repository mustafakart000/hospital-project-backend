package com.hospital.dto.PatientTreatments.request;

import java.util.List;

import com.hospital.dto.TestType;

import lombok.Data;

@Data
public class LaboratoryRequest {
    private Long appointmentId;
    private List<TestType> testTypes;
    private String priority;
    private String fastingStatus;
    private String notes;



}
