package com.chatsul.service.ReservationService;

import com.chatsul.domain.Reservation;
import org.springframework.data.domain.Page;

public interface ReservationQueryService {

    Page<Reservation> getReservationList(Long userId, Integer page);
}
