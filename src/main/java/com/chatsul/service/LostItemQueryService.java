package com.chatsul.service;

import com.chatsul.web.dto.LostItemResponseDTO;
import org.springframework.data.domain.Pageable;


public interface LostItemQueryService {
    LostItemResponseDTO getLostItems(Pageable pageable);

    LostItemResponseDTO.LostItemDetail getLostItemDetail(Long lostItemId);
}
