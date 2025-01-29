package com.chatsul.repository;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import com.chatsul.domain.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByMember(Member member, PageRequest pageRequest);

    Page<Reservation> findByMemberAndReservationDateAfter(Member member, LocalDate currentDate, Pageable pageable);
    Page<Reservation> findByVenueIdAndStatusAndReservationDateAfter(Long venueId, ReservationStatus status, LocalDate currentDate, Pageable pageable);
    Page<Reservation> findByVenueIdAndStatusNotAndReservationDateAfter(Long venueId, ReservationStatus excludedStatus, LocalDate currentDate, Pageable pageable);
}
