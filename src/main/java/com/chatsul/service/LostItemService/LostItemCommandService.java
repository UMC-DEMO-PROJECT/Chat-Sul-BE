package com.chatsul.service.LostItemService;

import com.chatsul.domain.LostItem;
import com.chatsul.domain.Member;
import com.chatsul.web.dto.LostItemRequestDTO;

public interface LostItemCommandService {
    LostItem saveLostItem(LostItemRequestDTO.RegisterLostItemRequestDTO request, Long venueId, Member member);

//    void deleteLostItem(LostItemRequestDTO lostItemRequestDTO);
//
    void updateLostItemStatus(Long lostItemId, Long venueId, Member member);
}
