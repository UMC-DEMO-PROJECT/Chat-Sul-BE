package com.chatsul.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.chatsul.domain.Member;
import com.chatsul.domain.Venue;
import com.chatsul.web.dto.VenueRequestDTO;
import com.chatsul.web.dto.VenueResponseDTO;

public class VenueConverter {

	public static Venue toCreateVenueDTO(VenueRequestDTO request, Member member, Double latitude, Double longitude) {
		return Venue.builder()
			.name(request.getName())
			.address(request.getAddress())
			.detailAddress(request.getDetailAddress())
			.phone(request.getPhone())
			.bank(request.getBank())
			.account(request.getAccount())
			.latitude(latitude)
			.longitude(longitude)
			.member(member)
			.build();
	}

	public static VenueResponseDTO.CreateVenueDTO VenueResultDTO(Venue venue) {
		return VenueResponseDTO.CreateVenueDTO.builder()
			.venueId(venue.getId())
			.name(venue.getName())
			.address(venue.getAddress())
			.detailAddress(venue.getDetailAddress())
			.phone(venue.getPhone())
			.bank(venue.getBank().getBankName())
			.account(venue.getAccount())
			.latitude(venue.getLatitude())
			.longitude(venue.getLongitude())
			.member(venue.getMember())
			.build();
	}

	public static VenueResponseDTO.VenueInfoDTO VenueInfoDTO(Venue venue) {
		return VenueResponseDTO.VenueInfoDTO.builder()
			.venueId(venue.getId())
			.name(venue.getName())
			.address(venue.getAddress())
			.detailAddress(venue.getDetailAddress())
			.phone(venue.getPhone())
			.build();
	}

	public static VenueResponseDTO.LocationListDTO locationListDTO(List<Venue> locationList) {
		List<VenueResponseDTO.LocationListDTO.MapLocationDTO> mapLocationDTOList = locationList.stream()
			.map(location -> VenueResponseDTO.LocationListDTO.MapLocationDTO.builder()
				.venueId(location.getId())
				.latitude(location.getLatitude())
				.longitude(location.getLongitude())
				.build())
			.collect(Collectors.toList());

		return VenueResponseDTO.LocationListDTO.builder()
			.locationList(mapLocationDTOList)
			.build();
	}
}