package com.hospital.mapper;

import org.springframework.stereotype.Component;
import com.hospital.dto.Response.MedicineResponse;
import com.hospital.dto.request.MedicineRequest;
import com.hospital.entity.Medicine;
import com.hospital.model.DoctorSpeciality;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class MedicineMapper {
    
    public static Medicine toEntity(MedicineRequest request) {
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setCategory(request.getCategory());
        medicine.setDescription(request.getDescription());
        medicine.setManufacturer(request.getManufacturer());
        medicine.setDosage(request.getDosage());
        medicine.setInsuranceCoverage(request.getInsuranceCoverage());
        medicine.setMg(request.getMg());
        medicine.setCoverage(request.getCoverage());
        medicine.setPrescriptionRequired(request.isPrescriptionRequired());
        
        // Güvenli dönüşüm için try-catch bloğu
        List<DoctorSpeciality> specialties = request.getRelatedSpecialties().stream()
            .map(spec -> {
                try {
                    return DoctorSpeciality.valueOf(spec.getCode());
                } catch (IllegalArgumentException e) {
                    // Geçersiz enum değeri durumunda loglama yapılabilir
                    return null;
                }
            })
            .filter(spec -> spec != null) // null değerleri filtrele
            .collect(Collectors.toList());
            
        medicine.setRelatedSpecialties(specialties);
        return medicine;
    }

    public static MedicineResponse toDto(Medicine medicine) {
        return MedicineResponse.builder()
            .id(medicine.getId())
            .name(medicine.getName())
            .category(medicine.getCategory())
            .description(medicine.getDescription())
            .manufacturer(medicine.getManufacturer())
            .dosage(medicine.getDosage())
            .insuranceCoverage(medicine.getInsuranceCoverage())
            .mg(medicine.getMg())
            .coverage(medicine.getCoverage())
            .isPrescriptionRequired(medicine.isPrescriptionRequired())
            .relatedSpecialties(medicine.getRelatedSpecialties().stream()
                .map(spec -> MedicineResponse.SpecialityDto.builder()
                    .code(spec.name())
                    .name(spec.getDisplayName())
                    .build())
                .collect(Collectors.toList()))
            .build();
    }
} 