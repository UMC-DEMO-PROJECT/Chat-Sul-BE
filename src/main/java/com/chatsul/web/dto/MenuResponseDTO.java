package com.chatsul.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MenuResponseDTO {

	// 메뉴 이미지 등록
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateMenuDTO {
		Long menuId;
		String imageUrl;
	}

	// 메뉴 이미지 반환
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MenuImageListDTO {
		private Long venueId;
		private List<MenuImageDTO> menuImageList;

		@Builder
		@Getter
		@NoArgsConstructor
		@AllArgsConstructor
		public static class MenuImageDTO {
			Long menuId;
			String imageUrl;
		}
	}
}
