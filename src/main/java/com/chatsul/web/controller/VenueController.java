package com.chatsul.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatsul.annotation.CurrentMember;
import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.converter.VenueConverter;
import com.chatsul.domain.Member;
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

	@Operation(summary = "매장(사업자) 데이터 생성 API", description = "매장 데이터를 저장하는 API입니다.<br>"
		+ "사용자 가입 및 로그인 후에 해당 사용자 계정으로 매장 데이터를 등록할 수 있습니다.<br>"
		+ "address는 카카오맵에서 검색한 주소를 입력해주세요.<br>"
		+ "account는 계좌번호입니다.<br>"
		+ "bank -> KAKAO_BANK: 카카오뱅크, SHINHAN_BANK: 신한은행, KB_BANK: 국민은행, WOORI_BANK: 우리은행, HANA_BANK: 하나은행, NH_BANK: 농협은행, IBK_BANK: 기업은행, SC_BANK: SC제일은행, CITI_BANK: 씨티은행")
	@PostMapping("/add")
	public ApiResponse<VenueResponseDTO.CreateVenueDTO> createVenue(@CurrentMember Member member,
		@RequestBody VenueRequestDTO request) {
		Venue venue = venueCommandService.createVenue(member, request);
		return ApiResponse.onSuccess(VenueConverter.VenueResultDTO(venue));
	}

	@Operation(summary = "위도, 경도 반환 API",
		description = "지도에 띄울 모든 매장 위치를 반환하는 API입니다.<br>"
			+ "위도, 경도 값이 Double 타입으로 반환됩니다.")
	@GetMapping("/map")
	public ApiResponse<VenueResponseDTO.LocationListDTO> getLocationList() {
		List<Venue> locationList = venueQueryService.getAllLocationList();
		return ApiResponse.onSuccess(VenueConverter.locationListDTO(locationList));
	}

	@Operation(summary = "매장 정보 반환 API",
		description = "매장 클릭 시 해당 매장의 정보를 반환하는 API입니다.")
	@GetMapping("/info/{venueId}")
	public ApiResponse<VenueResponseDTO.VenueInfoDTO> getVenueInfo(@PathVariable("venueId") Long venueId) {
		Venue venue = venueQueryService.getVenueInfo(venueId);
		return ApiResponse.onSuccess(VenueConverter.VenueInfoDTO(venue));
	}
}