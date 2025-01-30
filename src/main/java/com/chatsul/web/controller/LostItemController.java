package com.chatsul.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.chatsul.annotation.CurrentMember;
import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.converter.LostItemConverter;
import com.chatsul.domain.LostItem;
import com.chatsul.domain.Member;
import com.chatsul.service.LostItemService.LostItemQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatsul.web.dto.LostItemRequestDTO;
import com.chatsul.web.dto.LostItemResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lost-item")
@RequiredArgsConstructor
public class LostItemController {

	private final LostItemQueryService lostItemQueryService;

	//손님이 사용하는 API
	@Operation(summary = "손님용 분실물 목록 조회 API", description = "page에는 조회할 페이지목차를 입력하세요")
	@GetMapping("/member/list/{venueId}/{page}")
	public ApiResponse<LostItemResponseDTO.LostItemPreViewListDTO> getMemberLostItems(
			@PathVariable("venueId") Long venueId, @PathVariable("page") Integer page, @CurrentMember Member member) {
		Page<LostItem> lostItemList = lostItemQueryService.getLostItems(page, member, venueId);
		return ApiResponse.onSuccess(LostItemConverter.lostItemPreViewListDTO(lostItemList));
	}

	@Operation(summary = "손님용 분실물 세부조회 API", description = "조회할 분실물의 id를 입력하세요")
	@GetMapping("/member/{lostItemId}")
	public ResponseEntity<LostItemResponseDTO.LostItemDetail> getMemberLostItemDetail(
		@PathVariable(name = "lostItemId") Long lostItemId) {
		try {
			LostItemResponseDTO.LostItemDetail detail = lostItemService.getLostItemDetail(lostItemId);
			return ResponseEntity.ok(detail);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	//사장님이 사용하는 API
	@Operation(summary = "사장님용 분실물 목록 조회 API", description = "page에는 조회할 페이지 목차를 입력하세요")
	@GetMapping("/business/{page}")
	public ResponseEntity<LostItemResponseDTO> getLostItems(
		@PathVariable(name = "page") int page) {
		Pageable pageable = PageRequest.of(page - 1, 10);
		LostItemResponseDTO response = lostItemService.getLostItems(pageable);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "사장님용 분실물 세부조회 API", description = "조회할 분실물의 id를 입력하세요")
	@GetMapping("/business/{lostItemId}")
	public ResponseEntity<LostItemResponseDTO.LostItemDetail> getLostItemDetail(
		@PathVariable(name = "lostItemId") Long lostItemId) {
		try {
			LostItemResponseDTO.LostItemDetail detail = lostItemService.getLostItemDetail(lostItemId);
			return ResponseEntity.ok(detail);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "사장님용 분실물 등록 API")
	@PostMapping("/business/post")  //분실물 등록
	public ResponseEntity<String> createLostItem(@RequestBody LostItemRequestDTO requestDTO) {
		try {
			String response = lostItemService.saveLostItem(requestDTO);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/business/{lostItemId}")  //분실물 삭제
	public ResponseEntity<?> deleteLostItem(@PathVariable(name = "lostItemId") Long lostItemId) {
		LostItemRequestDTO requestDTO = LostItemRequestDTO.builder()
			.lostItemId(lostItemId)
			.build();
		lostItemService.deleteLostItem(requestDTO);

		Map<String, String> response = new HashMap<>();
		response.put("message", String.format("분실물 ID: %d가 성공적으로 삭제되었습니다.", lostItemId));

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "사장님용 수취상태 변경 API", description = "상태를 변경할 분실물의 id를 입력하세요")
	@PatchMapping("/business/{lostItemId}/status") //분실물 상태 변경
	public ResponseEntity<String> updateLostItemStatus(@PathVariable(name = "lostItemId") Long lostItemId) {
		try {
			String response = lostItemService.updateLostItemStatus(lostItemId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("상태 변경에 실패했습니다.");
		}
	}
}