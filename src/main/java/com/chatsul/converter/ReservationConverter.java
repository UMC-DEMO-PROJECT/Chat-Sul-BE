package com.chatsul.converter;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.ReservationStatus;
import com.chatsul.web.dto.ReservationRequestDTO;
import com.chatsul.web.dto.ReservationResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


public class ReservationConverter {

    public static ReservationResponseDTO.ReservationResultDTO toReservationResultDTO(Reservation reservation) {
        return ReservationResponseDTO.ReservationResultDTO.builder()
                .reservationId(reservation.getReservationId())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .venueName(reservation.getVenue().getName())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
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

    public static ReservationResponseDTO.ReservationPreViewDTO reservationPreViewDTO(Reservation reservation) {
        return ReservationResponseDTO.ReservationPreViewDTO.builder()
                .reservationId(reservation.getReservationId())
                .venueName(reservation.getVenue().getName())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .numberOfGuests(reservation.getNumberOfGuests())
                .status(reservation.getStatus())
                .build();
    }

    public static ReservationResponseDTO.ReservationPreViewListDTO reservationPreViewListDTO(Page<Reservation> reservationList) {
        List<ReservationResponseDTO.ReservationPreViewDTO> reservationPreViewDTOList = reservationList.stream()
                .map(ReservationConverter::reservationPreViewDTO)
                .collect(Collectors.toList());

        return ReservationResponseDTO.ReservationPreViewListDTO.builder()
                .reservationList(reservationPreViewDTOList)
                .listSize(reservationPreViewDTOList.size())
                .totalPage(reservationList.getTotalPages())
                .totalElements(reservationList.getTotalElements())
                .isFirst(reservationList.isFirst())
                .isLast(reservationList.isLast())
                .build();
    }

    public static ReservationResponseDTO.BusinessReservationPreViewDTO BusinessReservationPreViewDTO(Reservation reservation) {
        return ReservationResponseDTO.BusinessReservationPreViewDTO.builder()
                .reservationId(reservation.getReservationId())
                .reservationName(reservation.getReservationName())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .numberOfGuests(reservation.getNumberOfGuests())
                .status(reservation.getStatus())
                .build();
    }

    public static ReservationResponseDTO.BusinessReservationPreViewListDTO businessReservationPreViewListDTO(Page<Reservation> reservationList) {
        List<ReservationResponseDTO.BusinessReservationPreViewDTO> businessReservationPreViewDTOList = reservationList.stream()
                .map(ReservationConverter::BusinessReservationPreViewDTO)
                .collect(Collectors.toList());

        return ReservationResponseDTO.BusinessReservationPreViewListDTO.builder()
                .reservationList(businessReservationPreViewDTOList)
                .listSize(businessReservationPreViewDTOList.size())
                .totalPage(reservationList.getTotalPages())
                .totalElements(reservationList.getTotalElements())
                .isFirst(reservationList.isFirst())
                .isLast(reservationList.isLast())
                .build();
    }
}
