package com.hospital.mapper;



import com.hospital.dto.ReservationRequest;
import com.hospital.dto.Response.ReservationResponse;
import com.hospital.entity.Reservations;
import com.hospital.model.DoctorSpeciality;

public class ReservationsMapper {

    public static Reservations mapToEntity(ReservationRequest reservationRequest) {
        return Reservations.builder()
                .reservationDate(reservationRequest.getReservationDate())
                .reservationTime(reservationRequest.getReservationTime())
                .status(reservationRequest.getStatus())
                .speciality(DoctorSpeciality.getByDisplayName(reservationRequest.getSpeciality().trim()))
                .doctor(reservationRequest.getDoctor())
                .patient(reservationRequest.getPatient())
                .isTreated(false)
                .build();
    }

    public static Reservations mapToEntity(ReservationRequest reservationRequest, Reservations reservation) {
        reservation.setReservationDate(reservationRequest.getReservationDate());
        reservation.setReservationTime(reservationRequest.getReservationTime());
        reservation.setStatus(reservationRequest.getStatus());
        reservation.setSpeciality(DoctorSpeciality.getByDisplayName(reservationRequest.getSpeciality().trim()));
        reservation.setDoctor(reservationRequest.getDoctor());
        reservation.setPatient(reservationRequest.getPatient());
        return reservation;
    }

    public static ReservationResponse mapToResponse(Reservations reservation) {

        return ReservationResponse.builder()
                .id(reservation.getId())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .status(reservation.getStatus())
                .speciality(reservation.getSpeciality().getDisplayName())
                .doctorId(reservation.getDoctor().getId())
                .doctorName(reservation.getDoctor().getAd())
                .doctorSurname(reservation.getDoctor().getSoyad())
                .patientId(reservation.getPatient().getId())
                .patientName(reservation.getPatient().getAd())
                .patientSurname(reservation.getPatient().getSoyad())
                .isTreated(reservation.getIsTreated())
                .treatmentDate(reservation.getTreatmentDate())
                .build();
    }
}