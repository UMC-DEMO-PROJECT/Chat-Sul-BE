package com.chatsul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatsul.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findByVenueId(Long venueId);
}
