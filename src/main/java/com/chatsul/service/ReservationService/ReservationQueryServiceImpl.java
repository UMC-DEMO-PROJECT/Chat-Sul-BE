package com.chatsul.service.ReservationService;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.domain.enums.ReservationStatus;
import com.chatsul.repository.MemberRepository;
import com.chatsul.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryServiceImpl implements ReservationQueryService {

    private final ReservationRepository reservationRepository;

    @Override
    public Page<Reservation> getReservationList(Member member, Integer page) {
        if (member == null || member.getId() == null) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        LocalDate currentDate = LocalDate.now();

        // 예약 날짜 순으로 정렬 (오름차순)
        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.ASC, "reservationDate"));

        Page<Reservation> reservationPage = reservationRepository.findByMemberAndReservationDateAfter
                (member, currentDate, pageRequest);

        return reservationPage;
    }

    @Override
    public Page<Reservation> getBusinessReservationList(Long venueId, String status, Integer page) {

        LocalDate currentDate = LocalDate.now();

        // 예약 날짜 순으로 정렬 (오름차순)
        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.ASC, "reservationDate"));

        // 상태에 따른 필터링
        switch (status) {
            case "ALL":
                return getAllReservations(venueId, currentDate, pageRequest);

            case "CONFIRMED":
                return getReservationsByStatus(venueId, ReservationStatus.CONFIRMED, currentDate, pageRequest);

            case "WAITING_DEPOSIT":
                return getReservationsByStatus(venueId, ReservationStatus.WAITING_DEPOSIT, currentDate, pageRequest);

            case "WAITING_CONFIRMATION":
                return getReservationsByStatus(venueId, ReservationStatus.WAITING_CONFIRMATION, currentDate, pageRequest);

            default:
                throw new GeneralException(ErrorStatus._BAD_REQUEST); // 잘못된 요청 처리
        }
    }

    private Page<Reservation> getAllReservations(Long venueId, LocalDate currentDate, PageRequest pageRequest) {
        return reservationRepository.findByVenueIdAndStatusNotAndReservationDateAfter(
                venueId, ReservationStatus.CANCELLED, currentDate, pageRequest);
    }

    private Page<Reservation> getReservationsByStatus(Long venueId, ReservationStatus status, LocalDate currentDate, PageRequest pageRequest) {
        return reservationRepository.findByVenueIdAndStatusAndReservationDateAfter(
                venueId, status, currentDate, pageRequest);
    }
}
