package com.chatsul.service.VenueService;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.converter.VenueConverter;
import com.chatsul.domain.Member;
import com.chatsul.domain.Venue;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.VenueRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VenueCommandServiceImpl implements VenueCommandService {

	private final VenueRepository venueRepository;
	private final KakaoMapService kakaoMapService;

	@Override
	public Venue createVenue(Member member, VenueRequestDTO request) {
		if (member.isOwner()) {
			throw new GeneralException(ErrorStatus.MEMBER_ROLE_INVALID);
		}

		Map<String, Double> coordinates = kakaoMapService.getCoordinates(request.getAddress());

		Double latitude = coordinates.get("latitude");
		Double longitude = coordinates.get("longitude");

		Venue venue = VenueConverter.toCreateVenueDTO(request, member, latitude, longitude);

		member.registerVenueAndBecomeOwner(venue);

		return venueRepository.save(venue);
	}
}