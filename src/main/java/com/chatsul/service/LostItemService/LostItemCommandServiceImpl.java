package com.chatsul.service.LostItemService;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.domain.Member;
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

//	@Override
//	public void deleteLostItem(LostItemRequestDTO requestDTO) {
//		if (!lostItemRepository.existsById(requestDTO.getLostItemId())) {
//			throw new EntityNotFoundException("분실물을 찾을 수 없습니다.");
//		}
//
//		lostItemRepository.deleteByLostItemId(requestDTO.getLostItemId());
//	}
//
//	@Override
//	public String updateLostItemStatus(Long lostItemId) {
//		LostItem lostItem = lostItemRepository.findById(lostItemId)
//			.orElseThrow(() -> new EntityNotFoundException("분실물을 찾을 수 없습니다."));
//
//		lostItem.updateStatus();
//		return "분실물이 수취되었습니다.";
//	}

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
