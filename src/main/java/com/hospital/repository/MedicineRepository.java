package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hospital.entity.Medicine;
import com.hospital.model.DoctorSpeciality;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findAllMedicineByRelatedSpecialties(DoctorSpeciality speciality);

} 