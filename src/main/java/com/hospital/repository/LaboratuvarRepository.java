package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Laboratuary;
import com.hospital.model.Laboratuvar;



public interface LaboratuvarRepository extends JpaRepository<Laboratuary, Long> {
    boolean existsByLaboratuvar(Laboratuvar laboratuvar);
}
