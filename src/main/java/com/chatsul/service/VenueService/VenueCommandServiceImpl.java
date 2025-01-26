package com.chatsul.service.VenueService;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		Map<String, Double> coordinates = kakaoMapService.getCoordinates(request.getAddress());

		Double latitude = coordinates.get("latitude");
		Double longitude = coordinates.get("longitude");

		Venue venue = Venue.builder()
			.name(request.getName())
			.address(request.getAddress())
			.detailAddress(request.getDetailAddress())
			.phone(request.getPhone())
			.latitude(latitude)
			.longitude(longitude)
			.account(request.getAccount())
			.bank(request.getBank())
			.member(member)
			.build();

		member.updateRoleToOwner();

		return venueRepository.save(venue);
	}
}