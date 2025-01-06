package com.chatsul.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.converter.VenueConverter;
import com.chatsul.domain.Venue;
import com.chatsul.service.VenueService.VenueCommandService;
import com.chatsul.service.VenueService.VenueQueryService;
import com.chatsul.web.dto.VenueRequestDTO;
import com.chatsul.web.dto.VenueResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/venue")
@RequiredArgsConstructor
public class VenueController {

	private final VenueCommandService venueCommandService;
	private final VenueQueryService venueQueryService;

	@Operation(summary = "매장 데이터 생성 API", description = "매장 데이터를 저장하는 API입니다.<br>"
		+ "address는 카카오맵에서 검색한 주소를 입력해주세요.<br>"
		+ "account: 계좌번호, bank: 은행명")
	@PostMapping("/add")
	public ApiResponse<VenueResponseDTO.CreateVenueDTO> createVenue(@RequestBody VenueRequestDTO request) {
		Venue venue = venueCommandService.createVenue(request);
		return ApiResponse.onSuccess(VenueConverter.toCreateVenueDTO(venue));
	}

	@Operation(summary = "위도, 경도 반환 API",
		description = "지도에 띄울 모든 매장 위치를 반환하는 API입니다.<br>"
			+ "위도, 경도 값이 Double 타입으로 반환됩니다.")
	@GetMapping("/map")
	public ApiResponse<VenueResponseDTO.LocationListDTO> getLocationList() {
		List<Venue> locationList = venueQueryService.getAllLocationList();
		return ApiResponse.onSuccess(VenueConverter.locationListDTO(locationList));
	}
}