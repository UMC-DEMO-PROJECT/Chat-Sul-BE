package com.chatsul.service.ReservationService;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryServiceImpl implements ReservationQueryService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Page<Reservation> getReservationList(Long userId, Integer page) {
        Member member = memberRepository.findById(userId).get();
        LocalDate currentDate = LocalDate.now();

        // 예약 날짜 순으로 정렬 (오름차순)
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "reservationDate"));

        Page<Reservation> reservationPage = reservationRepository.findByMemberAndReservationDateAfter
                (member, currentDate, pageRequest);

        return reservationPage;
    }
}
