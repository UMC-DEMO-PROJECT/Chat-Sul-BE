package com.chatsul.service.ReservationService;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import org.springframework.data.domain.Page;

public interface ReservationQueryService {

    Page<Reservation> getReservationList(Member member, Integer page);
}
