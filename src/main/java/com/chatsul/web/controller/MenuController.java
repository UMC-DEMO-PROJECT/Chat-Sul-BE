package com.chatsul.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatsul.annotation.CurrentMember;
import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.converter.MenuConverter;
import com.chatsul.domain.Member;
import com.chatsul.domain.Menu;
import com.chatsul.service.MenuService.MenuCommandService;
import com.chatsul.service.MenuService.MenuQueryService;
import com.chatsul.web.dto.MenuRequestDTO;
import com.chatsul.web.dto.MenuResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

	private final MenuCommandService menuCommandService;
	private final MenuQueryService menuQueryService;

	@Operation(summary = "매장 메뉴 등록 API",
		description = "매장 메뉴를 등록하는 API입니다.<br>"
			+ "메뉴를 등록할 매장 id를 입력해주세요.<br>"
			+ "메뉴 이미지는 여러 장일 수 있습니다.")
	@PostMapping(value = "/add/{venueId}", consumes = "multipart/form-data")
	public ApiResponse<List<MenuResponseDTO.CreateMenuDTO>> createMenu(@ModelAttribute MenuRequestDTO request,
		@PathVariable("venueId") Long venueId, @CurrentMember Member member) {
		List<Menu> menuList = menuCommandService.createMenu(request, venueId, member);
		return ApiResponse.onSuccess(MenuConverter.menuResultDTO(menuList));
	}

	@Operation(summary = "메뉴 이미지 반환 API",
		description = "매장 메뉴 이미지를 반환하는 API입니다.<br>"
			+ "메뉴를 조회할 매장 id를 입력해주세요."
			+ "메뉴 이미지는 여러 장일 수 있습니다.")
	@GetMapping("/{venueId}")
	public ApiResponse<MenuResponseDTO.MenuImageListDTO> getMenuImageList(@PathVariable("venueId") Long venueId) {
		MenuResponseDTO.MenuImageListDTO imageList = menuQueryService.getMenuImageList(venueId);
		return ApiResponse.onSuccess(imageList);
	}

	@Operation(summary = "메뉴 이미지 삭제 API",
		description = "매장 메뉴 이미지를 삭제하는 API입니다.<br>"
			+ "삭제할 메뉴 id를 입력해주세요.")
	@DeleteMapping("/{venueId}/delete/{menuId}")
	public ApiResponse<String> deleteMenu(@PathVariable("venueId") Long venueId, @PathVariable("menuId") Long menuId,
		@CurrentMember Member member) {
		menuCommandService.deleteMenu(menuId, venueId, member);
		return ApiResponse.onSuccess("메뉴가 삭제되었습니다.");
	}
}