package com.chatsul.service.ReservationService;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.ReservationConverter;
import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.ReservationStatus;
import com.chatsul.domain.enums.Role;
import com.chatsul.repository.MemberRepository;
import com.chatsul.repository.ReservationRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.ReservationRequestDTO;
import com.chatsul.web.dto.ReservationResponseDTO;
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

    // 유저
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
    public ReservationResponseDTO.PhoneInfoDTO cancelReservation(Long reservationId, Member member) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.RESERVATION_NOT_FOUND));

        if (!reservation.getMember().equals(member)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        LocalDate currentDate = LocalDate.now();
        if (reservation.getReservationDate().isBefore(currentDate.plusDays(2))) {
            Venue venue = reservation.getVenue();
            return ReservationResponseDTO.PhoneInfoDTO.builder()
                    .phone(venue.getPhone())
                    .build();
            // 취소 실패 시 전화번호 반환
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return null; // 취소가 성공한 경우 null 반환
    }

    // 사장님
    @Override
    public void acceptReservation(Long reservationId, Long venueId, Member member) {
        handleReservationUpdate(reservationId, venueId, member, ReservationStatus.WAITING_DEPOSIT, ReservationStatus.WAITING_CONFIRMATION);
    }

    @Override
    public void rejectReservation(Long reservationId, Long venueId, Member member) {
        handleReservationUpdate(reservationId, venueId, member, ReservationStatus.WAITING_DEPOSIT, ReservationStatus.CANCELLED);
    }

    @Override
    public void confirmReservation(Long reservationId, Long venueId, Member member) {
        handleReservationUpdate(reservationId, venueId, member, ReservationStatus.WAITING_CONFIRMATION, ReservationStatus.CONFIRMED);
    }

    // 예약 업데이트
    private void handleReservationUpdate(Long reservationId, Long venueId, Member member, ReservationStatus expectedStatus, ReservationStatus updatedStatus) {
        Venue venue = getVenueById(venueId);
        validateOwner(member, venue);

        Reservation reservation = getReservationById(reservationId);
        validateReservationStatus(reservation, expectedStatus);

        reservation.setStatus(updatedStatus);
    }

    private Venue getVenueById(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));
    }

    private Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.RESERVATION_NOT_FOUND));
    }

    // 사장 확인
    private void validateOwner(Member member, Venue venue) {
        if (!member.getRole().equals(Role.OWNER)) {
            throw new GeneralException(ErrorStatus.MEMBER_ROLE_INVALID);
        }
        if (!venue.getMember().equals(member)) {
            throw new GeneralException(ErrorStatus.VENUE_MEMBER_MISMATCH);
        }
    }

    private void validateReservationStatus(Reservation reservation, ReservationStatus expectedStatus) {
        if (!reservation.getStatus().equals(expectedStatus)) {
            throw new GeneralException(ErrorStatus.INVALID_RESERVATION_STATUS);
        }
    }
}
