package com.chatsul.converter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.chatsul.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.chatsul.domain.LostItem;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.LostItemStatus;
import com.chatsul.web.dto.LostItemRequestDTO;
import com.chatsul.web.dto.LostItemResponseDTO;

public class LostItemConverter {

	public static LostItemResponseDTO.LostItemResultDTO toLostItemResultDTO(LostItem lostItem) {
		return LostItemResponseDTO.LostItemResultDTO.builder()
				.lostItemId(lostItem.getLostItemId())
				.title(lostItem.getTitle())
				.foundDate(lostItem.getFoundDate())
				.lostItemStatus(lostItem.getLostItemStatus())
				.createdAt(lostItem.getCreatedAt())
				.build();
	}

	public static LostItem toLostItem(LostItemRequestDTO.RegisterLostItemRequestDTO lostItem, Venue venue, Member member) {
		return LostItem.builder()
				.title(lostItem.getTitle())
				.description(lostItem.getDescription())
				.foundDate(LocalDate.now())
				.lostItemStatus(LostItemStatus.LOST)
				.venue(venue)
				.member(member)
				.build();
	}

	public static LostItemResponseDTO.LostItemPreViewDTO lostItemPreViewDTO(LostItem lostItem) {
		return LostItemResponseDTO.LostItemPreViewDTO.builder()
				.lostItemId(lostItem.getLostItemId())
				.title(lostItem.getTitle())
				.foundDate(lostItem.getFoundDate())
				.lostItemStatus(lostItem.getLostItemStatus())
				.venueName(lostItem.getVenue().getName())
				.venueAddress(lostItem.getVenue().getAddress())
				.venuePhone(lostItem.getVenue().getPhone())
				.build();
	}

	public static LostItemResponseDTO.LostItemPreViewListDTO lostItemPreViewListDTO(Page<LostItem> lostItemList) {
		List<LostItemResponseDTO.LostItemPreViewDTO> lostItemPreViewDTOList = lostItemList.stream()
				.map(LostItemConverter::lostItemPreViewDTO)
				.collect(Collectors.toList());

		return LostItemResponseDTO.LostItemPreViewListDTO.builder()
				.lostItemPreViewDTOList(lostItemPreViewDTOList)
				.listSize(lostItemPreViewDTOList.size())
				.totalPage(lostItemList.getTotalPages())
				.totalElements(lostItemList.getTotalElements())
				.isFirst(lostItemList.isFirst())
				.isLast(lostItemList.isLast())
				.build();
	}
}
