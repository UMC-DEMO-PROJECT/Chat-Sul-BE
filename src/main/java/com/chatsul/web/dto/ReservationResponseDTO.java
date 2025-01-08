package com.chatsul.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationResultDTO {
        Long reservationId;
        LocalDate reservationDate;
        LocalTime reservationTime;
    }
}
