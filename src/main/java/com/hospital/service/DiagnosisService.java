package com.hospital.service;

import java.util.List;

import com.hospital.dto.PatientTreatments.request.DiagnosisRequest;  

public interface DiagnosisService {
    DiagnosisRequest createDiagnosis(DiagnosisRequest request);
    DiagnosisRequest getDiagnosis(Long id);
    List<DiagnosisRequest> getDiagnosesByPatient(String patientId);
    DiagnosisRequest updateDiagnosis(Long id, DiagnosisRequest request);
    void deleteDiagnosis(Long id);
    
}
