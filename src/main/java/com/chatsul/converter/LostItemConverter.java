package com.chatsul.converter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.chatsul.aws.AmazonS3Manager;
import com.chatsul.domain.LostItem;
import com.chatsul.domain.Member;
import com.chatsul.domain.Uuid;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.LostItemStatus;
import com.chatsul.repository.UuidRepository;
import com.chatsul.web.dto.LostItemRequestDTO;
import com.chatsul.web.dto.LostItemResponseDTO;

public class LostItemConverter {

	public static LostItemResponseDTO.LostItemResultDTO toLostItemResultDTO(LostItem lostItem) {
		return LostItemResponseDTO.LostItemResultDTO.builder()
			.lostItemId(lostItem.getLostItemId())
			.title(lostItem.getTitle())
			.description(lostItem.getDescription())
			.foundDate(lostItem.getFoundDate())
			.lostItemStatus(lostItem.getLostItemStatus())
			.createdAt(lostItem.getCreatedAt())
			.build();
	}

	public static LostItem toLostItem(LostItemRequestDTO.RegisterLostItemRequestDTO lostItem, Venue venue,
		Member member, List<String> itemImg) {
		return LostItem.builder()
			.title(lostItem.getTitle())
			.description(lostItem.getDescription())
			.foundDate(LocalDate.now())
			.lostItemStatus(LostItemStatus.LOST)
			.itemImg(itemImg)
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
			.description(lostItem.getDescription())
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

	public static LostItemResponseDTO.LostItemDetailDTO lostItemDetailDTO(LostItem lostItem) {
		return LostItemResponseDTO.LostItemDetailDTO.builder()
			.lostItemId(lostItem.getLostItemId())
			.title(lostItem.getTitle())
			.description(lostItem.getDescription())
			.foundDate(lostItem.getFoundDate())
			.lostItemStatus(lostItem.getLostItemStatus())
			.itemImg(lostItem.getItemImg())
			.venueName(lostItem.getVenue().getName())
			.build();
	}

	public static List<String> multipartFilesToUrls(List<MultipartFile> files, UuidRepository uuidRepository,
		AmazonS3Manager s3Manager) {
		return files.stream()
			.map(file -> {
				String uuid = UUID.randomUUID().toString();
				Uuid saveUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
				return s3Manager.uploadFile(s3Manager.generateLostItemKeyName(saveUuid), file);
			})
			.collect(Collectors.toList());
	}
}
