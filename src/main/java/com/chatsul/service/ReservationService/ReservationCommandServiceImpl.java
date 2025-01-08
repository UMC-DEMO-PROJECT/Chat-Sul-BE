package com.chatsul.service.ReservationService;

import com.chatsul.converter.ReservationConverter;
import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.domain.Venue;
import com.chatsul.repository.MemberRepository;
import com.chatsul.repository.ReservationRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.ReservationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final VenueRepository venueRepository;
    private final MemberRepository memberRepository;

    @Override
    public Reservation createReservation(ReservationRequestDTO.MakeReservationRequestDTO request) {

        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new IllegalArgumentException("매장 정보가 존재하지 않습니다."));
        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));

        Reservation reservation = ReservationConverter.toReservation(request, venue, member);

        return reservationRepository.save(reservation);
    }
}
