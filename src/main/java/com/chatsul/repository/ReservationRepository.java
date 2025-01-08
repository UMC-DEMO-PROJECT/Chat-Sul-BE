package com.chatsul.repository;

import com.chatsul.domain.Member;
import com.chatsul.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByMember(Member member, PageRequest pageRequest);
}
