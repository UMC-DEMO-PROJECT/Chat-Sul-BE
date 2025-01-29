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
	public static class LostItemDTO { //LostItem을 LostItemDTO로 변환
		private Long lostItemId;
		private String title;
		private LocalDate foundDate;
		private String lostItemStatus;
		private Long venueId;
		private String venueName;
		private String venueAddress;
		private String venuePhone;

		public static LostItemDTO from(LostItem entity) {
			Venue venue = entity.getVenue();
			return LostItemDTO.builder()
				.lostItemId(entity.getLostItemId())
				.title(entity.getTitle())
				.foundDate(entity.getFoundDate())
				.lostItemStatus(entity.getLostItemStatus() == LostItemStatus.Lost ? "미수취" : "완료")
				.venueId(entity.getVenue().getVenueId())
				.venueName(entity.getVenue().getName())
				.venueAddress(entity.getVenue().getAddress())
				.venuePhone(entity.getVenue().getPhone())
				.build();
		}
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

		public static LostItemDetail toDetailItem(LostItem entity) {

			String status;
			if (entity.getLostItemStatus() == null) {
				status = "미수취";
			} else {
				status = entity.getLostItemStatus().toString().equals("Lost") ? "미수취" : "완료";
			}

			return LostItemDetail.builder()
				.lostItemId(entity.getLostItemId())
				.title(entity.getTitle())
				.foundDate(entity.getFoundDate())
				.lostItemStatus((entity.getLostItemStatus() == LostItemStatus.Lost ? "미수취" : "완료"))
				.itemImg(entity.getItemImg())
				.description(entity.getDescription())
				.build();
		}
	}

	public static LostItemResponseDTO LostItemList(Page<LostItem> page) {

		int totalElements = (int)page.getTotalElements();
		int totalPages = (totalElements + PAGE_SIZE - 1) / PAGE_SIZE;

		return LostItemResponseDTO.builder()
			.content(page.getContent().stream()
				.map(LostItemDTO::from)
				.collect(Collectors.toList()))
			.currentPage(page.getNumber() + 1)
			.totalPages(page.getTotalPages())
			.totalElements(page.getTotalElements())
			.hasNext(page.hasNext())
			.build();
	}

	public static LostItemDetail detail(LostItem entity) {
		return LostItemDetail.toDetailItem(entity);
	}

}
