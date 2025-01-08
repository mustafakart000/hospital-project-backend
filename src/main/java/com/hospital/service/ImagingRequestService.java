package com.hospital.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import com.hospital.repository.ImagingRequestRepository;
import com.hospital.entity.ImagingRequest;
import com.hospital.entity.Patient;
import com.hospital.entity.Doctor;
import com.hospital.dto.ImagingRequestDTO;
import com.hospital.dto.ImagingRequestResponseDTO;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.DoctorRepository;

@Service
@RequiredArgsConstructor
public class ImagingRequestService {
    private final ImagingRequestRepository imagingRequestRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ImagingRequestResponseDTO createRequest(ImagingRequestDTO requestDTO) {
        ImagingRequest request = new ImagingRequest();
        request.setImagingType(requestDTO.getImagingType());
        request.setPriority(requestDTO.getPriority());
        request.setNotes(requestDTO.getNotes());
        request.setBodyPart(requestDTO.getBodyPart());
        
        Patient patient = patientRepository.findById(requestDTO.getPatientId())
            .orElseThrow(() -> new RuntimeException("Hasta bulunamadı"));
        request.setPatient(patient);
        
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
            .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));
        request.setDoctor(doctor);
        
        ImagingRequest savedRequest = imagingRequestRepository.save(request);
        return convertToResponseDTO(savedRequest);
    }

    public ImagingRequestResponseDTO updateRequest(Long id, ImagingRequestDTO requestDTO) {
        ImagingRequest existingRequest = imagingRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Görüntüleme talebi bulunamadı"));
        
        existingRequest.setImagingType(requestDTO.getImagingType());
        existingRequest.setNotes(requestDTO.getNotes());
        existingRequest.setPriority(requestDTO.getPriority());
        existingRequest.setBodyPart(requestDTO.getBodyPart());
        
        ImagingRequest savedRequest = imagingRequestRepository.save(existingRequest);
        return convertToResponseDTO(savedRequest);
    }

    public List<ImagingRequestResponseDTO> getPatientRequests(Long patientId) {
        return imagingRequestRepository.findByPatientId(patientId).stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    public List<ImagingRequestResponseDTO> getDoctorRequests(Long doctorId) {
        return imagingRequestRepository.findByDoctorId(doctorId).stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    public void deleteRequest(Long id) {
        imagingRequestRepository.deleteById(id);
    }

    private ImagingRequestResponseDTO convertToResponseDTO(ImagingRequest request) {
        ImagingRequestResponseDTO responseDTO = new ImagingRequestResponseDTO();
        responseDTO.setId(request.getId());
        responseDTO.setImagingType(request.getImagingType());
        responseDTO.setPriority(request.getPriority());
        responseDTO.setNotes(request.getNotes());
        responseDTO.setBodyPart(request.getBodyPart());
        responseDTO.setCreatedAt(request.getCreatedAt());
        
        if (request.getPatient() != null) {
            responseDTO.setPatientId(request.getPatient().getId());
            responseDTO.setPatientName(request.getPatient().getAd() + " " + request.getPatient().getSoyad());
        }
        
        if (request.getDoctor() != null) {
            responseDTO.setDoctorId(request.getDoctor().getId());
            responseDTO.setDoctorName(request.getDoctor().getAd() + " " + request.getDoctor().getSoyad());
        }
        
        return responseDTO;
    }
} 