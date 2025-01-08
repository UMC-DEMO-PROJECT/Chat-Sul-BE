package com.chatsul.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDTO {

    @Getter
    public static class MakeReservationRequestDTO {

        private String reservationName;
        private String phoneNumber;
        private LocalDate reservationDate;
        @JsonFormat(pattern = "HH:mm")
        private LocalTime reservationTime;
        @Min(value = 1, message = "최소 1명 이상 입력해주세요")
        private int numberOfGuests;
        private String depositorName;
        private Long userId;
    }
}
