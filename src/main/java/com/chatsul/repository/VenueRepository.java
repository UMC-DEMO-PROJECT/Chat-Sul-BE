package com.chatsul.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatsul.domain.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}