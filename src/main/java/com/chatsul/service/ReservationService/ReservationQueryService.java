package com.chatsul.service.ReservationService;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.web.dto.ReservationResponseDTO;
import org.springframework.data.domain.Page;

public interface ReservationQueryService {

    Page<Reservation> getReservationList(Member member, Integer page);
    ReservationResponseDTO.AccountInfoDTO getReservationAccountInfo(Long reservationId, Member member);
    Page<Reservation> getBusinessReservationList(Long venueId, String status, Integer page, Member member);
}
