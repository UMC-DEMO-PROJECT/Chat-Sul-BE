package com.chatsul.web.dto;

import com.chatsul.domain.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ReservationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationResultDTO {
        Long reservationId;
        LocalDate reservationDate;
        LocalTime reservationTime;
        String venueName;
        ReservationStatus status;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationPreViewDTO { // 사용자 예약 확인
        String venueName;
        Long reservationId;
        LocalDate reservationDate;
        LocalTime reservationTime;
        int numberOfGuests;
        ReservationStatus status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationPreViewListDTO {
        List<ReservationPreViewDTO> reservationList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessReservationPreViewDTO { // 사장님 예약 확인
        String reservationName; // 예약자명
        Long reservationId;
        LocalDate reservationDate;
        LocalTime reservationTime;
        int numberOfGuests;
        ReservationStatus status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessReservationPreViewListDTO {
        List<BusinessReservationPreViewDTO> reservationList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}
