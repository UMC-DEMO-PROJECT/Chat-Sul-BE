package com.chatsul.service.LostItemService;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.domain.Member;
import com.chatsul.domain.enums.LostItemStatus;
import com.chatsul.domain.enums.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.converter.LostItemConverter;
import com.chatsul.domain.LostItem;
import com.chatsul.domain.Venue;
import com.chatsul.repository.LostItemRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.LostItemRequestDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LostItemCommandServiceImpl implements LostItemCommandService {
	private final LostItemRepository lostItemRepository;
	private final VenueRepository venueRepository;

	@Override
	public LostItem saveLostItem(LostItemRequestDTO.RegisterLostItemRequestDTO request, Long venueId, Member member) {

		Venue venue = venueRepository.findById(venueId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

		validateOwner(member, venue);

		LostItem lostItem = LostItemConverter.toLostItem(request, venue, member);

		return lostItemRepository.save(lostItem);
	}

	@Override
	public void deleteLostItem(Long lostItemId, Long venueId, Member member) {

		Venue venue = venueRepository.findById(venueId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

		validateOwner(member, venue);

		LostItem lostItem = lostItemRepository.findById(lostItemId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.LostItem_NOT_FOUND));

		if (!lostItem.getVenue().equals(venue)) {
			throw new GeneralException(ErrorStatus.LOST_ITEM_VENUE_MISMATCH);
		}

		lostItemRepository.delete(lostItem);
	}

	@Override
	public void updateLostItemStatus(Long lostItemId, Long venueId, Member member) {

		LostItem lostItem = lostItemRepository.findById(lostItemId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LostItem_NOT_FOUND));
		Venue venue = venueRepository.findById(venueId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));
		validateOwner(member, venue);

		lostItem.updateStatus();
		lostItemRepository.save(lostItem);
	}

	// 사장 확인
	private void validateOwner(Member member, Venue venue) {
		if (!member.getRole().equals(Role.OWNER)) {
			throw new GeneralException(ErrorStatus.MEMBER_ROLE_INVALID);
		}
		if (!venue.getMember().equals(member)) {
			throw new GeneralException(ErrorStatus.VENUE_MEMBER_MISMATCH);
		}
	}

}
