package com.chatsul.service;

import com.chatsul.domain.LostItem;
import com.chatsul.repository.LostItemRepository;
import com.chatsul.web.dto.LostItemResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LostItemQueryServiceImpl implements LostItemQueryService {
    private final LostItemRepository lostItemRepository;

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
}
