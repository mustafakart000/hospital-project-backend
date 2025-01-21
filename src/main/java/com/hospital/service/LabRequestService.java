package com.hospital.service;

import com.hospital.dto.LabRequestDTO;
import com.hospital.dto.LabResultDTO;
import com.hospital.entity.LabRequest;
import com.hospital.entity.LabTestPdf;
import com.hospital.entity.Patient;
import com.hospital.enums.FastingStatus;
import com.hospital.enums.Priority;
import com.hospital.enums.Status;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.LabRequestRepository;
import com.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LabRequestService {
    private final LabRequestRepository labRequestRepository;
    private final PatientRepository patientRepository;

    public LabRequest createLabRequest(LabRequestDTO dto) {
        LabRequest labRequest = new LabRequest();
        
        Patient patient = patientRepository.findById(Long.parseLong(dto.getPatientId()))
            .orElseThrow(() -> new ResourceNotFoundException("Hasta bulunamadı"));
        labRequest.setPatient(patient);
        
        labRequest.setDoctorId(dto.getDoctorId());
        labRequest.setReservationId(dto.getReservationId());
        labRequest.setTestPanel(dto.getTestPanel());
        labRequest.setPriority(Priority.valueOf(dto.getPriority()));
        labRequest.setFastingStatus(FastingStatus.valueOf(dto.getFastingStatus()));
        labRequest.setNotes(dto.getNotes());
        
        return labRequestRepository.save(labRequest);
    }

    public List<LabRequest> getLabRequestsByPatient(Long patientId) {
        return labRequestRepository.findByPatient_Id(patientId);
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

    public List<LabRequest> getPendingRequests() {
        List<LabRequest> requests = labRequestRepository.findByStatus(Status.PENDING);
        requests.forEach(request -> {
            if (request.getPatient() != null) {
                // Hibernate proxy'yi gerçek nesneye dönüştür
                request.getPatient().getUsername();
            }
        });
        return requests;
    }

    public LabRequest completeLabRequest(Long requestId, LabResultDTO resultDTO) {
        LabRequest request = labRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Lab isteği bulunamadı"));

        System.out.println("Lab Request Status güncelleniyor: " + Status.COMPLETED);
        request.setStatus(Status.COMPLETED);
        request.setResults(resultDTO.getResults());
        request.setNotes(resultDTO.getNotes());
        request.setCompletedAt(LocalDateTime.now());
        request.setTechnicianId(resultDTO.getTechnicianId());

        // Test PDF'lerini kaydet
        if (resultDTO.getTestPdfs() != null && !resultDTO.getTestPdfs().isEmpty()) {
            List<LabTestPdf> testPdfs = resultDTO.getTestPdfs().stream()
                .map(pdfDTO -> {
                    LabTestPdf pdf = new LabTestPdf();
                    pdf.setTestType(pdfDTO.getTestType());
                    pdf.setPdfUrl(pdfDTO.getPdfUrl());
                    pdf.setPdfData(pdfDTO.getPdfData());
                    pdf.setStatus(Status.COMPLETED);
                    pdf.setCreatedAt(LocalDateTime.now());
                    pdf.setCompletedAt(LocalDateTime.now());
                    return pdf;
                })
                .collect(Collectors.toList());
            
            request.setTestPdfs(testPdfs);
        }

        LabRequest savedRequest = labRequestRepository.save(request);
        System.out.println("Kaydedilen Lab Request Status: " + savedRequest.getStatus());
        if (savedRequest.getTestPdfs() != null) {
            savedRequest.getTestPdfs().forEach(pdf -> 
                System.out.println("Kaydedilen PDF Status: " + pdf.getStatus())
            );
        }
        return savedRequest;
    }

    public List<LabTestPdf> getLabTestPdfsByPatientId(Long patientId, List<Status> statusList) {
        List<LabRequest> labRequests = labRequestRepository.findByPatient_Id(patientId);
        List<LabTestPdf> allPdfs = new ArrayList<>();
        
        for (LabRequest request : labRequests) {
            if (request.getTestPdfs() != null) {
                List<LabTestPdf> pdfs = request.getTestPdfs();
                pdfs.forEach(pdf -> {
                    if (pdf.getStatus() == null) {
                        pdf.setStatus(request.getStatus());
                    }
                    if (pdf.getCreatedAt() == null) {
                        pdf.setCreatedAt(request.getCreatedAt());
                    }
                    if (pdf.getCompletedAt() == null && request.getStatus() == Status.COMPLETED) {
                        pdf.setCompletedAt(request.getCompletedAt());
                    }
                });
                
                // Status filtresi ekle
                if (statusList != null && !statusList.isEmpty()) {
                    pdfs = pdfs.stream()
                        .filter(pdf -> statusList.contains(pdf.getStatus()))
                        .collect(Collectors.toList());
                }
                
                allPdfs.addAll(pdfs);
            }
        }
        
        return allPdfs;
    }

    public byte[] getLabTestPdfDataById(Long pdfId, Long patientId) {
        List<LabRequest> labRequests = labRequestRepository.findByPatient_Id(patientId);
        
        for (LabRequest request : labRequests) {
            if (request.getTestPdfs() != null) {
                for (LabTestPdf pdf : request.getTestPdfs()) {
                    if (pdf.getId().equals(pdfId)) {
                        return pdf.getPdfData();
                    }
                }
            }
        }
        
        throw new ResourceNotFoundException("PDF bulunamadı");
    }

    public List<LabRequest> getAllPatientLabRequests(Long patientId, List<Status> statusList) {
        List<LabRequest> allRequests = labRequestRepository.findByPatient_Id(patientId);
        
        // Status filtresi uygula
        if (statusList != null && !statusList.isEmpty()) {
            return allRequests.stream()
                .filter(request -> statusList.contains(request.getStatus()))
                .collect(Collectors.toList());
        }
        
        return allRequests;
    }

    public List<LabRequest> getAllLabRequests(List<Status> statusList) {
        List<LabRequest> allRequests = labRequestRepository.findAll();
        
        // Status filtresi uygula
        if (statusList != null && !statusList.isEmpty()) {
            return allRequests.stream()
                .filter(request -> statusList.contains(request.getStatus()))
                .collect(Collectors.toList());
        }
        
        return allRequests;
    }
} 