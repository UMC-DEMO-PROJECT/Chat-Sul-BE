package com.chatsul.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    WAITING_DEPOSIT("입금 대기"),
    WAITING_CONFIRMATION("확정 대기"),
    CONFIRMED("확정"),
    CANCELLED("취소");

    private final String status;
}
