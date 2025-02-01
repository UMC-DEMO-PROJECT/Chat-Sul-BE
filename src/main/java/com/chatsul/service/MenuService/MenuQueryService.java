package com.chatsul.service.MenuService;

import com.chatsul.web.dto.MenuResponseDTO;

public interface MenuQueryService {

	MenuResponseDTO.MenuImageListDTO getMenuImageList(Long venueId);
}
