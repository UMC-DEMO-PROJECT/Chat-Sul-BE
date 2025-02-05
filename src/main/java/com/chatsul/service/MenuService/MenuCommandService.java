package com.chatsul.service.MenuService;

import java.util.List;

import com.chatsul.domain.Menu;
import com.chatsul.web.dto.MenuRequestDTO;

public interface MenuCommandService {

	List<Menu> createMenu(MenuRequestDTO request, Long venueId);

	void deleteMenu(Long menuId, Long venueId);
}