package com.chatsul.service.LostItemService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.aws.AmazonS3Manager;
import com.chatsul.converter.LostItemConverter;
import com.chatsul.domain.LostItem;
import com.chatsul.domain.Member;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.Role;
import com.chatsul.repository.LostItemRepository;
import com.chatsul.repository.UuidRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.LostItemRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LostItemCommandServiceImpl implements LostItemCommandService {
	private final LostItemRepository lostItemRepository;
	private final VenueRepository venueRepository;
	private final AmazonS3Manager s3Manager;
	private final UuidRepository uuidRepository;

	@Override
	public LostItem saveLostItem(LostItemRequestDTO.RegisterLostItemRequestDTO request, Long venueId, Member member) {
		if (request.getItemImg() == null) {
			request.setItemImg(new ArrayList<>());
		}

		Venue venue = venueRepository.findById(venueId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

		validateOwner(member, venue);

		List<String> imageUrlList = LostItemConverter.multipartFilesToUrls(
			request.getItemImg(), uuidRepository, s3Manager);

		LostItem lostItem = LostItemConverter.toLostItem(request, venue, member, imageUrlList);

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
		if (!lostItem.getVenue().equals(venue)) {
			throw new GeneralException(ErrorStatus.LOST_ITEM_VENUE_MISMATCH);
		}
		validateOwner(member, venue);

		lostItem.updateStatus();
		lostItemRepository.save(lostItem);
	}

	@Override
	public LostItem updateLostItem(LostItemRequestDTO.UpdateLostItemRequestDTO request, Long lostItemId, Long venueId,
		Member member) {

		LostItem lostItem = lostItemRepository.findById(lostItemId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LostItem_NOT_FOUND));
		Venue venue = venueRepository.findById(venueId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

		validateOwner(member, venue);

		if (!lostItem.getVenue().equals(venue)) {
			throw new GeneralException(ErrorStatus.LOST_ITEM_VENUE_MISMATCH);
		}

		if (request.getTitle() != null)
			lostItem.updateTitle(request.getTitle());
		if (request.getItemImg() != null) {
			List<String> imageUrlList = LostItemConverter.multipartFilesToUrls(
				request.getItemImg(), uuidRepository, s3Manager);

			lostItem.updateItemImg(imageUrlList);
		}
		if (request.getDescription() != null)
			lostItem.updateDescription(request.getDescription());
		if (request.getFoundDate() != null)
			lostItem.updateFoundDate(request.getFoundDate());

		return lostItemRepository.save(lostItem);
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
