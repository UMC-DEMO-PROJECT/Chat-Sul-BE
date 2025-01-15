package com.chatsul.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.web.dto.LostItemRequestDTO;
import com.chatsul.web.dto.LostItemResponseDTO;

@Service
@Transactional
public interface LostItemService {

	String saveLostItem(LostItemRequestDTO lostItemRequestDTO);

	LostItemResponseDTO getLostItems(Pageable pageable);

	LostItemResponseDTO.LostItemDetail getLostItemDetail(Long lostItemId);

	void deleteLostItem(LostItemRequestDTO lostItemRequestDTO);

	String updateLostItemStatus(Long lostItemId);
}