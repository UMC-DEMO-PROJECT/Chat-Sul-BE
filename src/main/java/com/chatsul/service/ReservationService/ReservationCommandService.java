package com.chatsul.service.ReservationService;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.web.dto.ReservationRequestDTO;

public interface ReservationCommandService {

    Reservation createReservation(ReservationRequestDTO.MakeReservationRequestDTO request, Long venueId, Member member);
    void cancelReservation(Long reservationId, Member member);
    void acceptReservation(Long reservationId, Long venueId);
}
