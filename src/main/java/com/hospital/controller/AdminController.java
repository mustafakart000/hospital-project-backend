package com.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hospital.dto.AdminByAdminRequest;
import com.hospital.dto.DoctorRequest;
import com.hospital.dto.Response.AdminResponseList;
import com.hospital.dto.Response.DoctorResponseList;
import com.hospital.dto.Response.GetFullDoctorResponse;
import com.hospital.service.AdminService;
import com.hospital.service.DoctorService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/doctor/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<DoctorResponseList> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
    //localhost:8080/doctor/delete/1
    @DeleteMapping("/doctor/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
    //localhost:8080/doctor/update/1
    // json body hazırlayınız

    //localhost:8080/doctor/update/1
    @PutMapping("/doctor/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<HttpStatus> updateDoctor(@PathVariable Long id, @RequestBody DoctorRequest doctorRequest) {
        doctorService.updateDoctor(id, doctorRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    //localhost:8080/doctor/get/1
    @GetMapping("/doctor/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public GetFullDoctorResponse getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }
    //gelAllAdmin
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminResponseList> getAllAdmin() {
        return adminService.getAllAdmin();
    }
    //localhost:8080/admin/delete/1
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }

    // 
   

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminResponseList> getAdminById(@PathVariable Long id) {
        AdminResponseList admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> updateAdmin(@PathVariable Long id, @RequestBody AdminByAdminRequest request) {
        adminService.updateAdmin(id, request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
   
}
