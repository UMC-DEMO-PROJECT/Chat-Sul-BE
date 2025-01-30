package com.chatsul.web.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.chatsul.domain.LostItem;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.LostItemStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class LostItemResponseDTO {
	@NotNull
	private List<LostItemDTO> content;
	private int currentPage;
	private int totalPages;
	private long totalElements;
	private boolean hasNext;

	public static final int PAGE_SIZE = 6;

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
		String venueAddress;
		String venuePhone;
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


	@Getter
	@Builder
	public static class LostItemDetail {  //상세 조회
		private Long lostItemId;
		private String title;
		private LocalDate foundDate;
		private String lostItemStatus;
		private String itemImg;
		private String description;

	}

	public static LostItemResponseDTO LostItemList(Page<LostItem> page) {
		return null;
	}

	public static LostItemDetail detail(LostItem entity) {
		return LostItemDetail.toDetailItem(entity);
	}

}
