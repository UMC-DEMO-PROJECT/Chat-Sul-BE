package com.chatsul.service.ReservationService;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.web.dto.ReservationRequestDTO;

public interface ReservationCommandService {

    Reservation createReservation(ReservationRequestDTO.MakeReservationRequestDTO request, Long venueId, Member member);
    void cancelReservation(Long reservationId, Member member); // 사용자가 예약 취소
    void acceptReservation(Long reservationId, Long venueId);
    void rejectReservation(Long reservationId, Long venueId); // 사장님이 예약 거절
}
