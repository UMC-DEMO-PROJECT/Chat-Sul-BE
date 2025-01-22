package com.chatsul.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.chatsul.domain.Menu;
import com.chatsul.domain.Venue;
import com.chatsul.web.dto.VenueResponseDTO;

public class VenueConverter {

	public static VenueResponseDTO.CreateVenueDTO toCreateVenueDTO(Venue venue) {
		return VenueResponseDTO.CreateVenueDTO.builder()
			.venueId(venue.getId())
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

	public static VenueResponseDTO.MenuImageListDTO menuImageListDTO(List<Menu> menuImageList) {
		List<VenueResponseDTO.MenuImageListDTO.MenuImageDTO> menuImageDTOList = menuImageList.stream()
			.map(menuImage -> VenueResponseDTO.MenuImageListDTO.MenuImageDTO.builder()
				.menuId(menuImage.getId())
				.imageUrl(menuImage.getImageUrl())
				.build())
			.collect(Collectors.toList());

		return VenueResponseDTO.MenuImageListDTO.builder()
			.menuImageList(menuImageDTOList)
			.build();
	}
}