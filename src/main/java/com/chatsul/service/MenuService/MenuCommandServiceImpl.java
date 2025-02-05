package com.chatsul.service.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.aws.AmazonS3Manager;
import com.chatsul.converter.MenuConverter;
import com.chatsul.domain.Member;
import com.chatsul.domain.Menu;
import com.chatsul.domain.Uuid;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.Role;
import com.chatsul.repository.MenuRepository;
import com.chatsul.repository.UuidRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.MenuRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuCommandServiceImpl implements MenuCommandService {

	private final UuidRepository uuidRepository;
	private final MenuRepository menuRepository;
	private final VenueRepository venueRepository;
	private final AmazonS3Manager s3Manager;

	@Override
	public List<Menu> createMenu(MenuRequestDTO request, Long venueId, Member member) {
		if (request.getImageUrl() == null) {
			request.setImageUrl(new ArrayList<>());
		}

		List<String> imageUrlList = new ArrayList<>();

		Venue venue = venueRepository.findById(venueId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

		validateOwner(member, venue);

		for (MultipartFile image : request.getImageUrl()) {
			String uuid = UUID.randomUUID().toString();
			Uuid saveUuid = uuidRepository.save(Uuid.builder()
				.uuid(uuid).build());

			String imageUrl = s3Manager.uploadFile(s3Manager.generateMenuKeyName(saveUuid), image);

			imageUrlList.add(imageUrl);
		}

		List<Menu> menuList = MenuConverter.toCreateMenuDTO(imageUrlList, venue);

		return menuRepository.saveAll(menuList);
	}

	@Override
	public void deleteMenu(Long menuId, Long venueId, Member member) {
		Venue venue = venueRepository.findById(venueId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.MENU_NOT_FOUND));

		validateOwner(member, venue);

		if (!menu.getVenue().equals(venue)) {
			throw new GeneralException(ErrorStatus.MENU_VENUE_MISMATCH);
		}

		menuRepository.delete(menu);
	}

	private void validateOwner(Member member, Venue venue) {
		if (!member.getRole().equals(Role.OWNER)) {
			throw new GeneralException(ErrorStatus.MEMBER_ROLE_INVALID);
		}
		if (!venue.getMember().equals(member)) {
			throw new GeneralException(ErrorStatus.VENUE_MEMBER_MISMATCH);
		}
	}
}
