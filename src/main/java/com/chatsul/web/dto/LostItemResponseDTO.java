package com.chatsul.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.chatsul.domain.enums.LostItemStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class LostItemResponseDTO {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LostItemResultDTO {
		Long lostItemId;
		String title;
		String description;
		LocalDate foundDate;
		LostItemStatus lostItemStatus;
		LocalDateTime createdAt;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LostItemPreViewDTO { //LostItem을 LostItemDTO로 변환
		Long lostItemId;
		String title;
		LocalDate foundDate;
		LostItemStatus lostItemStatus;
		String venueName;
		String description;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LostItemPreViewListDTO {
		List<LostItemPreViewDTO> lostItemPreViewDTOList;
		Integer listSize;
		Integer totalPage;
		Long totalElements;
		Boolean isFirst;
		Boolean isLast;
	}

	/*@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LostItemDetailDTO {  //상세 조회
		private Long lostItemId;
		private String title;
		private LocalDate foundDate;
		private LostItemStatus lostItemStatus;
		private List<String> itemImg;
		private String description;
		private String venueName;
	}*/

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LostItemDetailDTO {  //상세 조회
		private Long lostItemId;
		private String title;
		private LocalDate foundDate;
		private LostItemStatus lostItemStatus;
		private String itemImg;
		private String description;
		private String venueName;
	}

}
