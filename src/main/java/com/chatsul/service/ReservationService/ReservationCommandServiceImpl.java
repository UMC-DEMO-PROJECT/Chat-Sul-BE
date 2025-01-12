package com.chatsul.service.ReservationService;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.ReservationConverter;
import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.ReservationStatus;
import com.chatsul.repository.MemberRepository;
import com.chatsul.repository.ReservationRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.ReservationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final VenueRepository venueRepository;
    private final MemberRepository memberRepository;

    @Override
    public Reservation createReservation(ReservationRequestDTO.MakeReservationRequestDTO request, Long venueId) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new IllegalArgumentException("매장 정보가 존재하지 않습니다."));

        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));

        Reservation reservation = ReservationConverter.toReservation(request, venue, member);

        return reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(Long reservationId, Long userId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약 정보가 존재하지 않습니다."));

        if (!reservation.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("취소 권한이 없습니다.");
        }

        LocalDate currentDate = LocalDate.now();
        if (reservation.getReservationDate().isBefore(currentDate.plusDays(2))) {
            throw new GeneralException(ErrorStatus.CANCEL_RESERVATION_BEFORE_2DAYS);
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
    }
}
