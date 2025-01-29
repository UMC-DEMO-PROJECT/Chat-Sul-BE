package com.chatsul.service.LostItemService;

import com.chatsul.web.dto.LostItemRequestDTO;

public interface LostItemCommandService {
    String saveLostItem(LostItemRequestDTO lostItemRequestDTO);

    void deleteLostItem(LostItemRequestDTO lostItemRequestDTO);

    String updateLostItemStatus(Long lostItemId);
}
