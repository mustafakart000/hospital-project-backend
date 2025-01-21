package com.hospital.mapper;

import com.hospital.dto.TechnicianRequest;
import com.hospital.dto.TechnicianResponse;
import com.hospital.entity.Technician;
import com.hospital.model.Role;
import org.springframework.stereotype.Component;

@Component
public class TechnicianMapper {
    
    public Technician toEntity(TechnicianRequest request) {
        Technician technician = new Technician();
        technician.setAd(request.getName());
        technician.setSoyad(request.getSurname());
        technician.setUsername(request.getUsername());
        technician.setEmail(request.getEmail());
        technician.setPassword(request.getPassword());
        technician.setTelefon(request.getPhoneNumber());
        technician.setDepartment(request.getDepartment());
        technician.setSpecialization(request.getSpecialization());
        technician.setBirthDate(request.getBirthDate());
        technician.setTcKimlik(request.getTcKimlik());
        technician.setAdres(request.getAdres());
        technician.setKanGrubu(request.getKanGrubu());
        technician.setRole(Role.TECHNICIAN.name());
      
        return technician;
    }
    
    public TechnicianResponse toResponse(Technician technician) {
        return new TechnicianResponse(
            technician.getId(),
            technician.getAd(),
            technician.getSoyad(),
            technician.getUsername(),
            technician.getEmail(),
            technician.getTcKimlik(),
            technician.getAdres(),
            technician.getKanGrubu(),
            technician.getTelefon(),
            technician.getDepartment(),
            technician.getBirthDate(),
            technician.getSpecialization()
        );
    }
} 