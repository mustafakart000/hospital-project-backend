package com.hospital.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.hospital.model.Role;
import java.util.List;
import com.hospital.dto.Response.MedicineResponse;
import com.hospital.service.MedicineService;
import com.hospital.dto.request.MedicineRequest;
@RestController
@RequestMapping("/medicine")
public class MedicineController {

    private final MedicineService medicineService;
    
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<MedicineResponse> getMedicineById(@PathVariable Long id) {
        MedicineResponse response = medicineService.getMedicineById(id);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/getAllByDoctorSpeciality")
    public ResponseEntity<List<MedicineResponse>> getAllByDoctorSpeciality() {
        List<MedicineResponse> response = medicineService.getAllByDoctorSpeciality();
        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN' , 'DOCTOR')")
    @PostMapping("/createAll")
    public ResponseEntity<List<MedicineResponse>> createAllMedicine(@RequestBody List<MedicineRequest> request) {
        List<MedicineResponse> response = medicineService.createAllMedicine(request);
        return ResponseEntity.ok(response);
    }
}
