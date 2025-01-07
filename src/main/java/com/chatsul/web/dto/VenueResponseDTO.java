package com.chatsul.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VenueResponseDTO {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateVenueDTO {
		Long venueId;
	}

	// 매장 클릭 시 반환
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VenueInfoDTO {
		String name;
		String address;
		String phone;
		String account;
		String bank;
	}

	// 지도 위치 표시할 때 반환
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LocationListDTO {
		private List<MapLocationDTO> locationList;

		@Builder
		@Getter
		@NoArgsConstructor
		@AllArgsConstructor
		public static class MapLocationDTO {
			Double latitude;
			Double longitude;
		}
	}
}