package com.chatsul.repository;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByMember(Member member, PageRequest pageRequest);

    Page<Reservation> findByMemberAndReservationDateAfter(Member member, LocalDate currentDate, Pageable pageable);
}
