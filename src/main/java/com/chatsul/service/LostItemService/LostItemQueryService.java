package com.chatsul.service.LostItemService;

import com.chatsul.domain.LostItem;
import com.chatsul.domain.Member;
import com.chatsul.web.dto.LostItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LostItemQueryService {
    Page<LostItem> getLostItems(Integer page, Member member, Long venueId);

    LostItemResponseDTO.LostItemDetailDTO getLostItemDetail(Long lostItemId, Member member, Long venueId);
}
