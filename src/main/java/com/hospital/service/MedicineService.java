package com.hospital.service;

import com.hospital.dto.Response.MedicineResponse;
import com.hospital.entity.Medicine;
import com.hospital.entity.Doctor;
import com.hospital.repository.MedicineRepository;
import com.hospital.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import com.hospital.dto.request.MedicineRequest;
import com.hospital.mapper.MedicineMapper;
import com.hospital.model.DoctorSpeciality;
import com.hospital.repository.DoctorRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final DoctorRepository doctorRepository;
    public MedicineService(MedicineRepository medicineRepository, DoctorRepository doctorRepository) {
        this.medicineRepository = medicineRepository;
        this.doctorRepository = doctorRepository;
    }

    public MedicineResponse getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Medicine not found with id: " + id));
        return MedicineMapper.toDto(medicine);
    }

    public List<MedicineResponse> createAllMedicine(List<MedicineRequest> requests) {
        List<Medicine> medicines = requests.stream()
            .map(MedicineMapper::toEntity)
            .collect(Collectors.toList());
        
        List<Medicine> savedMedicines = medicineRepository.saveAll(medicines);
        
        return savedMedicines.stream()
            .map(MedicineMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<MedicineResponse> getAllByDoctorSpeciality() {
        // Giriş yapmış doktorun bilgilerini al
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Doctor doctor = doctorRepository.findByUsername(currentUser.getUsername())
            .orElseThrow(() -> new NotFoundException("Doctor not found"));
        
        // Doktorun uzmanlık alanını kullan
        DoctorSpeciality doctorSpeciality = doctor.getUzmanlik();
        List<Medicine> medicines = medicineRepository.findAllMedicineByRelatedSpecialties(doctorSpeciality);
        return medicines.stream()
            .map(MedicineMapper::toDto)
            .collect(Collectors.toList());
    }
} 