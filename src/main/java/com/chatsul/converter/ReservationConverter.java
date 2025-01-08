package com.chatsul.converter;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.ReservationStatus;
import com.chatsul.web.dto.ReservationRequestDTO;
import com.chatsul.web.dto.ReservationResponseDTO;

import java.time.LocalDateTime;


public class ReservationConverter {

    public static ReservationResponseDTO.ReservationResultDTO toReservationResultDTO(Reservation reservation) {
        return ReservationResponseDTO.ReservationResultDTO.builder()
                .reservationId(reservation.getReservationId())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .build();
    }
    public static Reservation toReservation(ReservationRequestDTO.MakeReservationRequestDTO reservation, Venue venue, Member member) {
        return Reservation.builder()
                .reservationName(reservation.getReservationName())
                .phoneNumber(reservation.getPhoneNumber())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .depositorName(reservation.getDepositorName())
                .numberOfGuests(reservation.getNumberOfGuests())
                .status(ReservationStatus.WAITING_DEPOSIT)
                .venue(venue)
                .member(member)
                .build();
    }
}
