package com.chatsul.service.ReservationService;

import com.chatsul.domain.Reservation;
import com.chatsul.web.dto.ReservationRequestDTO;

public interface ReservationCommandService {

    Reservation createReservation(ReservationRequestDTO.MakeReservationRequestDTO request, Long venueId);
}
