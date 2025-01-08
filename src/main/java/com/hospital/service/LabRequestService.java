package com.hospital.service;

import com.hospital.dto.LabRequestDTO;
import com.hospital.entity.LabRequest;
import com.hospital.enums.FastingStatus;
import com.hospital.enums.Priority;
import com.hospital.enums.Status;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.LabRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LabRequestService {
    private final LabRequestRepository labRequestRepository;

    public LabRequest createLabRequest(LabRequestDTO dto) {
        LabRequest labRequest = new LabRequest();
        labRequest.setPatientId(dto.getPatientId());
        labRequest.setDoctorId(dto.getDoctorId());
        labRequest.setReservationId(dto.getReservationId());
        labRequest.setTestPanels(dto.getTestPanels());
        labRequest.setPriority(Priority.valueOf(dto.getPriority()));
        labRequest.setFastingStatus(FastingStatus.valueOf(dto.getFastingStatus()));
        labRequest.setNotes(dto.getNotes());
        
        return labRequestRepository.save(labRequest);
    }

    public List<LabRequest> getLabRequestsByPatient(String patientId) {
        return labRequestRepository.findByPatientId(patientId);
    }

    public List<LabRequest> getLabRequestsByDoctor(String doctorId) {
        return labRequestRepository.findByDoctorId(doctorId);
    }

    public LabRequest updateLabRequestStatus(Long requestId, Status newStatus) {
        LabRequest labRequest = labRequestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Lab request not found"));
        
        labRequest.setStatus(newStatus);
        return labRequestRepository.save(labRequest);
    }

    public void deleteLabRequest(Long requestId) {
        labRequestRepository.deleteById(requestId);
    }
} 