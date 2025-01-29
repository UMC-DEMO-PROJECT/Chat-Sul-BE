package com.chatsul.converter;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.chatsul.domain.LostItem;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.LostItemStatus;
import com.chatsul.web.dto.LostItemRequestDTO;
import com.chatsul.web.dto.LostItemResponseDTO;

@Component
public class LostItemConverter {

	public LostItem toEntity(LostItemRequestDTO dto, Venue venue) {
		return new LostItem(
			dto.getTitle(),
			LocalDate.now(),
			dto.getDescription(),
			dto.getItemImg(),
			LostItemStatus.Lost, // 기본 상태를 Lost로 설정
			venue
		);
	}

	public LostItemResponseDTO toDto(Page<LostItem> lostItems) {
		return LostItemResponseDTO.builder()
			.content(lostItems.getContent().stream()
				.map(LostItemResponseDTO.LostItemDTO::from)
				.collect(Collectors.toList()))
			.currentPage(lostItems.getNumber() + 1)
			.totalPages(lostItems.getTotalPages())
			.totalElements(lostItems.getTotalElements())
			.hasNext(lostItems.hasNext())
			.build();
	}
}
