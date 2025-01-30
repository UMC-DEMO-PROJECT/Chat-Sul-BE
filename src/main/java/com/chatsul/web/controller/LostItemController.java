package com.chatsul.web.controller;

import com.chatsul.annotation.CurrentMember;
import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.converter.LostItemConverter;
import com.chatsul.domain.LostItem;
import com.chatsul.domain.Member;
import com.chatsul.service.LostItemService.LostItemCommandService;
import com.chatsul.service.LostItemService.LostItemQueryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.chatsul.web.dto.LostItemRequestDTO;
import com.chatsul.web.dto.LostItemResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lost-item")
@RequiredArgsConstructor
public class LostItemController {

	private final LostItemQueryService lostItemQueryService;
	private final LostItemCommandService lostItemCommandService;

	//손님이 사용하는 API
	@Operation(summary = "손님용 분실물 목록 조회 API", description = "page에는 조회할 페이지목차를 입력하세요")
	@GetMapping("/member/{venueId}/list/{page}")
	public ApiResponse<LostItemResponseDTO.LostItemPreViewListDTO> getMemberLostItems(
			@PathVariable("venueId") Long venueId, @PathVariable("page") Integer page, @CurrentMember Member member) {
		Page<LostItem> lostItemList = lostItemQueryService.getLostItems(page, member, venueId);
		return ApiResponse.onSuccess(LostItemConverter.lostItemPreViewListDTO(lostItemList));
	}

	@Operation(summary = "손님용 분실물 세부조회 API", description = "조회할 분실물의 id를 입력하세요")
	@GetMapping("/member/{venueId}/detail/{lostItemId}")
	public ApiResponse<LostItemResponseDTO.LostItemDetailDTO> getMemberLostItemDetail(
			@PathVariable("venueId") Long venueId , @PathVariable("lostItemId") Long lostItemId, @CurrentMember Member member) {
		LostItemResponseDTO.LostItemDetailDTO lostItemDetail = lostItemQueryService.getLostItemDetailByMember(lostItemId, member, venueId);
		return ApiResponse.onSuccess(lostItemDetail);
	}

	//사장님이 사용하는 API
	@Operation(summary = "사장님용 분실물 목록 조회 API", description = "page에는 조회할 페이지 목차를 입력하세요")
	@GetMapping("/business/{venueId}/list/{page}")
	public ApiResponse<LostItemResponseDTO.LostItemPreViewListDTO> getLostItems(
			@PathVariable("venueId") Long venueId, @PathVariable("page") Integer page, @CurrentMember Member member) {
		Page<LostItem> lostItems = lostItemQueryService.getLostItemsByBusiness(page, member, venueId);
		return ApiResponse.onSuccess(LostItemConverter.lostItemPreViewListDTO(lostItems));
	}

	@Operation(summary = "사장님용 분실물 세부조회 API", description = "조회할 분실물의 id를 입력하세요")
	@GetMapping("/business/{venueId}/detail/{lostItemId}")
	public ApiResponse<LostItemResponseDTO.LostItemDetailDTO> getLostItemDetail(
		@PathVariable("venueId") Long venueId , @PathVariable("lostItemId") Long lostItemId, @CurrentMember Member member) {
		LostItemResponseDTO.LostItemDetailDTO lostItemDetail = lostItemQueryService.getLostItemDetail(lostItemId, member, venueId);
		return ApiResponse.onSuccess(lostItemDetail);
	}

	@Operation(summary = "사장님용 분실물 등록 API", description = "이미지 등록은 아직 지원하지 않습니다.")
	@PostMapping("/business/{venueId}/post")  //분실물 등록
	public ApiResponse<LostItemResponseDTO.LostItemResultDTO> createLostItem(
			@PathVariable("venueId") Long venueId,
			@RequestBody @Valid LostItemRequestDTO.RegisterLostItemRequestDTO request, @CurrentMember Member member) {
		LostItem lostItem = lostItemCommandService.saveLostItem(request, venueId, member);
		return ApiResponse.onSuccess(LostItemConverter.toLostItemResultDTO(lostItem));
	}

	@Operation(summary = "사장님용 분실물 삭제 API")
	@DeleteMapping("/business/{venueId}/delete/{lostItemId}")
	public ApiResponse<String> deleteLostItem(
			@PathVariable("venueId") Long venueId , @PathVariable(name = "lostItemId") Long lostItemId, @CurrentMember Member member) {
		lostItemCommandService.deleteLostItem(lostItemId, venueId, member);
		return ApiResponse.onSuccess("분실물이 삭제되었습니다.");
	}

	@Operation(summary = "사장님용 수취상태 변경 API", description = "상태를 변경할 분실물의 id를 입력하세요")
	@PatchMapping("/business/{venueId}/status/{lostItemId}")
	public ApiResponse<String> updateLostItemStatus(
			@PathVariable("venueId") Long venueId, @PathVariable("lostItemId") Long lostItemId, @CurrentMember Member member) {
		lostItemCommandService.updateLostItemStatus(lostItemId, venueId, member);
		return ApiResponse.onSuccess("분실물 수취 상태가 변경되었습니다.");
	}
}