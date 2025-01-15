package com.chatsul.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.converter.LostItemConverter;
import com.chatsul.domain.LostItem;
import com.chatsul.repository.LostItemRepository;
import com.chatsul.web.dto.LostItemRequestDTO;
import com.chatsul.web.dto.LostItemResponseDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LostItemServiceImpl implements LostItemService {
	private final LostItemRepository lostItemRepository;
	private final LostItemConverter lostItemConverter;

	@Override
	@Transactional
	public String saveLostItem(LostItemRequestDTO requestDTO) {
		LostItem lostItem = lostItemConverter.toEntity(requestDTO);
		lostItemRepository.save(lostItem);
		return "분실물이 등록되었습니다.";
	}

	@Override
	public LostItemResponseDTO getLostItems(Pageable pageable) {
		Page<LostItem> lostItems = lostItemRepository.findAllByOrderByCreatedAtDesc(pageable);
		return LostItemResponseDTO.LostItemList(lostItems);
	}

	@Override
	public LostItemResponseDTO.LostItemDetail getLostItemDetail(Long lostItemId) {
		LostItem lostItem = lostItemRepository.findById(lostItemId)
			.orElseThrow(() -> new EntityNotFoundException("분실물을 찾을 수 없습니다."));
		return LostItemResponseDTO.LostItemDetail.toDetailItem(lostItem);
	}

	@Override
	@Transactional
	public void deleteLostItem(LostItemRequestDTO requestDTO) {
		log.info("Deleting lost item with ID: {}", requestDTO.getLostItemId());

		if (!lostItemRepository.existsById(requestDTO.getLostItemId())) {
			throw new EntityNotFoundException("분실물을 찾을 수 없습니다.");
		}

		lostItemRepository.deleteByLostItemId(requestDTO.getLostItemId());
		lostItemRepository.flush();

		log.info("Lost item deleted successfully");
	}

	@Override
	@Transactional
	public String updateLostItemStatus(Long lostItemId) {
		LostItem lostItem = lostItemRepository.findById(lostItemId)
			.orElseThrow(() -> new EntityNotFoundException("분실물을 찾을 수 없습니다."));

		lostItem.updateStatus();
		return "분실물이 수취되었습니다.";
	}
}
