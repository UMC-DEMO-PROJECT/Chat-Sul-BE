package com.chatsul.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.chatsul.domain.Menu;
import com.chatsul.domain.Venue;
import com.chatsul.web.dto.MenuResponseDTO;

public class MenuConverter {

	public static List<Menu> toCreateMenuDTO(List<String> imageUrl, Venue venue) {
		return imageUrl.stream()
			.map(image -> Menu.builder()
				.imageUrl(image)
				.venue(venue)
				.build())
			.collect(Collectors.toList());
	}

	public static List<MenuResponseDTO.CreateMenuDTO> menuListResultDTO(List<Menu> menuList) {
		return menuList.stream()
			.map(menu -> MenuResponseDTO.CreateMenuDTO.builder()
				.menuId(menu.getId())
				.imageUrl(menu.getImageUrl())
				.build())
			.collect(Collectors.toList());
	}

	public static MenuResponseDTO.CreateMenuDTO menuResultDTO(Menu menu) {
		return MenuResponseDTO.CreateMenuDTO.builder()
			.menuId(menu.getId())
			.imageUrl(menu.getImageUrl())
			.build();
	}

	public static List<MenuResponseDTO.MenuImageListDTO.MenuImageDTO> menuImageDTO(List<Menu> imageList) {
		return imageList.stream()
			.map(menu -> MenuResponseDTO.MenuImageListDTO.MenuImageDTO.builder()
				.menuId(menu.getId())
				.imageUrl(menu.getImageUrl())
				.build())
			.collect(Collectors.toList());
	}

	public static MenuResponseDTO.MenuImageListDTO menuImageListDTO(Venue venue, List<Menu> imageList) {
		List<MenuResponseDTO.MenuImageListDTO.MenuImageDTO> menuImageDTOList = menuImageDTO(imageList);

		return MenuResponseDTO.MenuImageListDTO.builder()
			.venueId(venue.getId())
			.menuImageList(menuImageDTOList)
			.build();
	}
}
