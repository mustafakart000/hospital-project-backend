package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hospital.entity.Reservations;
import com.hospital.model.DoctorSpeciality;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


public interface ReservationsRepository extends JpaRepository<Reservations, Long> {

    Optional<Reservations> findByReservationDateAndReservationTime(LocalDate reservationDate, LocalTime reservationTime);

    List<Reservations> findAllBySpeciality(DoctorSpeciality speciality);
    
    List<Reservations> findAllByDoctorId(Long doctorId);

    List<Reservations> findAllByPatientId(Long patientId);

    List<Reservations> findAllByReservationDateAndIsTreatedFalse(LocalDate date);
    List<Reservations> findAllByReservationDateAndIsTreatedTrue(LocalDate date);

    @Modifying
    @Query("UPDATE Reservations r SET r.isTreated = false WHERE r.isTreated IS NULL")
    @jakarta.transaction.Transactional
    void updateNullIsTreatedToFalse();
}
