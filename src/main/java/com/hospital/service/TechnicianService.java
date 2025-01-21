package com.hospital.service;

import com.hospital.dto.TechnicianRequest;
import com.hospital.dto.TechnicianResponse;
import java.util.List;

public interface TechnicianService {
    TechnicianResponse createTechnician(TechnicianRequest request);
    List<TechnicianResponse> getAllTechnicians();
    TechnicianResponse getTechnicianById(Long id);
    TechnicianResponse updateTechnician(Long id, TechnicianRequest request);
    void deleteTechnician(Long id);
} 