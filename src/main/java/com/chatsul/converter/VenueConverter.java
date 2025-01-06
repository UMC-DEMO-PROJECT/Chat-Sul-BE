package com.chatsul.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.chatsul.domain.Venue;
import com.chatsul.web.dto.VenueResponseDTO;

public class VenueConverter {

	public static VenueResponseDTO.CreateVenueDTO toCreateVenueDTO(Venue venue) {
		return VenueResponseDTO.CreateVenueDTO.builder()
			.venueId(venue.getId())
			.build();
	}

	public static VenueResponseDTO.LocationListDTO locationListDTO(List<Venue> locationList) {
		List<VenueResponseDTO.LocationListDTO.MapLocationDTO> mapLocationDTOList = locationList.stream()
			.map(location -> VenueResponseDTO.LocationListDTO.MapLocationDTO.builder()
				.latitude(location.getLatitude())
				.longitude(location.getLongitude())
				.build())
			.collect(Collectors.toList());

		return VenueResponseDTO.LocationListDTO.builder()
			.locationList(mapLocationDTOList)
			.build();
	}
}