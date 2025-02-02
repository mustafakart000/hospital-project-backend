package com.hospital.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.hospital.repository.ImagingRequestRepository;
import com.hospital.entity.ImagingRequest;
import com.hospital.entity.Patient;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.entity.Doctor;
import com.hospital.dto.ImagingRequestDTO;
import com.hospital.dto.ImagingRequestResponseDTO;
import com.hospital.dto.ImagingResultDTO;
import com.hospital.dto.RequestStatus;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.DoctorRepository;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImagingRequestService {
    private final ImagingRequestRepository imagingRequestRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final RestTemplate restTemplate = new RestTemplate();

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
        responseDTO.setImagingUrl(request.getImageUrl());
        responseDTO.setImageData(request.getImageData());
        responseDTO.setFindings(request.getFindings());
        responseDTO.setStatus(request.getStatus());
        
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

    public List<ImagingRequest> getPendingRequests() {
        return imagingRequestRepository.findByStatus(RequestStatus.PENDING);
    }

    public ImagingRequest completeImagingRequest(Long requestId, ImagingResultDTO resultDTO) {
        ImagingRequest request = imagingRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Görüntüleme isteği bulunamadı"));

        request.setStatus(RequestStatus.COMPLETED);
        request.setImageUrl(resultDTO.getImageUrl());
        
        
        // Base64 string'i byte array'e çevir
        if (resultDTO.getImageData() != null && !resultDTO.getImageData().isEmpty()) {
            byte[] decodedImage = Base64.getDecoder().decode(resultDTO.getImageData());
            request.setImageData(decodedImage);
        }
        
        request.setFindings(resultDTO.getFindings());
        request.setNotes(resultDTO.getNotes());
        request.setCompletedAt(LocalDateTime.now());
        request.setTechnicianId(Long.parseLong(resultDTO.getTechnicianId()));

        return imagingRequestRepository.save(request);
    }

    public List<ImagingRequest> getCompletedRequests() {
        return imagingRequestRepository.findByStatus(RequestStatus.COMPLETED);
    }

    public List<ImagingRequest> getPatientImagingRequests(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Hasta bulunamadı"));
        List<ImagingRequest> requests = imagingRequestRepository.findByPatientId(patientId);
        requests.forEach(request -> request.setPatient(patient));
        return requests;
    }

    public List<ImagingRequest> getAllImagingRequests(List<RequestStatus> statusList) {
        List<ImagingRequest> allRequests = imagingRequestRepository.findAll();
        
        if (statusList != null && !statusList.isEmpty()) {
            return allRequests.stream()
                .filter(request -> statusList.contains(request.getStatus()))
                .collect(Collectors.toList());
        }
        
        return allRequests;
    }

    public ImagingRequestResponseDTO getPatientImageRequest(Long patientId, Long imageId) {
        System.out.println("Görüntüleme isteği aranıyor - Patient ID: " + patientId + ", Image ID: " + imageId);
        
        ImagingRequest request = imagingRequestRepository.findById(imageId)
            .orElseThrow(() -> {
                System.out.println("Görüntüleme isteği bulunamadı - Image ID: " + imageId);
                return new ResourceNotFoundException("Görüntüleme isteği bulunamadı");
            });
        
        System.out.println("Görüntüleme isteği bulundu - Image ID: " + imageId);
        System.out.println("Hasta kontrolü yapılıyor - Beklenen Patient ID: " + patientId + ", Bulunan Patient ID: " + request.getPatient().getId());
        
        if (!request.getPatient().getId().equals(patientId)) {
            System.out.println("Hasta eşleşmesi başarısız - Beklenen: " + patientId + ", Bulunan: " + request.getPatient().getId());
            throw new ResourceNotFoundException("Bu görüntüleme isteği belirtilen hastaya ait değil");
        }
        
        System.out.println("Hasta doğrulaması başarılı, DTO dönüştürülüyor");
        ImagingRequestResponseDTO responseDTO = convertToResponseDTO(request);
        System.out.println("DTO dönüştürme başarılı - Image Data " + (responseDTO.getImageData() != null ? "mevcut" : "mevcut değil"));
        
        return responseDTO;
    }

    public byte[] getImageData(Long patientId, Long imageId) {
        System.out.println("getImageData çağrıldı - Patient ID: " + patientId + ", Image ID: " + imageId);
        
        ImagingRequest request = imagingRequestRepository.findById(imageId)
            .orElseThrow(() -> new ResourceNotFoundException("Görüntüleme isteği bulunamadı"));
            
        System.out.println("Görüntüleme isteği bulundu - Status: " + request.getStatus());
        System.out.println("Image URL: " + request.getImageUrl());
        System.out.println("Image Data: " + (request.getImageData() != null ? request.getImageData().length + " bytes" : "null"));
            
        if (!request.getPatient().getId().equals(patientId)) {
            throw new ResourceNotFoundException("Bu görüntüleme isteği belirtilen hastaya ait değil");
        }
        
        return request.getImageData();
    }
} 