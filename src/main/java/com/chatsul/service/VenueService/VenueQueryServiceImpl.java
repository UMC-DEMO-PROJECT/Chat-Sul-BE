package com.chatsul.service.VenueService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.domain.Venue;
import com.chatsul.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VenueQueryServiceImpl implements VenueQueryService {

	private final VenueRepository venueRepository;

	@Override
	public List<Venue> getAllLocationList() {
		return venueRepository.findAll();
	}
}