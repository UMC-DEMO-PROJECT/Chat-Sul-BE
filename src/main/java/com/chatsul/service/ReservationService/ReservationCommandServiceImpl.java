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

    @Override
    public Reservation createReservation(ReservationRequestDTO.MakeReservationRequestDTO request, Long venueId, Member member) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

        if (member == null || member.getId() == null) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        Reservation reservation = ReservationConverter.toReservation(request, venue, member);

        return reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(Long reservationId, Member member) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.RESERVATION_NOT_FOUND));

        if (!reservation.getMember().equals(member)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        LocalDate currentDate = LocalDate.now();
        if (reservation.getReservationDate().isBefore(currentDate.plusDays(2))) {
            throw new GeneralException(ErrorStatus.CANCEL_RESERVATION_BEFORE_2DAYS);
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
    }

    @Override
    public void acceptReservation(Long reservationId, Long venueId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.RESERVATION_NOT_FOUND));

        if (!reservation.getStatus().equals(ReservationStatus.WAITING_DEPOSIT)) {
            throw new GeneralException(ErrorStatus.INVALID_RESERVATION_STATUS);
        }

        reservation.setStatus(ReservationStatus.WAITING_CONFIRMATION);
    }
}
