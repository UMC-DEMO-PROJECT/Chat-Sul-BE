package com.chatsul.service.MenuService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.MenuConverter;
import com.chatsul.domain.Menu;
import com.chatsul.domain.Venue;
import com.chatsul.repository.MenuRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.MenuResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuQueryServiceImpl implements MenuQueryService {

	private final MenuRepository menuRepository;
	private final VenueRepository venueRepository;

	@Override
	public MenuResponseDTO.MenuImageListDTO getMenuImageList(Long venueId) {
		List<Menu> imageList = menuRepository.findByVenueId(venueId);

		Venue venue = venueRepository.findById(venueId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

		if (imageList.isEmpty()) {
			throw new GeneralException(ErrorStatus.MENU_NOT_FOUND);
		}

		return MenuConverter.menuImageListDTO(venue, imageList);
	}
}
