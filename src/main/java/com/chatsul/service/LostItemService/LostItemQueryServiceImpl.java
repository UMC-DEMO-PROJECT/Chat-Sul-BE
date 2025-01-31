package com.chatsul.service.LostItemService;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.LostItemConverter;
import com.chatsul.domain.LostItem;
import com.chatsul.domain.Member;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.Role;
import com.chatsul.repository.LostItemRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.LostItemResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LostItemQueryServiceImpl implements LostItemQueryService {
    private final LostItemRepository lostItemRepository;
    private final VenueRepository venueRepository;

    // 손님용 분실물 조회
    @Override
    public Page<LostItem> getLostItems(Integer page, Member member, Long venueId) {

        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.ASC, "foundDate"));
        Page<LostItem> lostItems = lostItemRepository.findAllByVenueId(venueId, pageRequest);


        return lostItems;
    }

    @Override
    public LostItemResponseDTO.LostItemDetailDTO getLostItemDetailByMember(Long lostItemId, Member member, Long venueId) {
        LostItem lostItem = lostItemRepository.findById(lostItemId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LostItem_NOT_FOUND));

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

        if (!lostItem.getVenue().equals(venue)) {
            throw new GeneralException(ErrorStatus.LOST_ITEM_VENUE_MISMATCH);
        }

        return LostItemConverter.lostItemDetailDTO(lostItem);
    }

    // 사장님용 분실물 조회
    @Override
    public Page<LostItem> getLostItemsByBusiness(Integer page, Member member, Long venueId) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

        validateOwner(member, venue);

        PageRequest pageRequest = PageRequest.of(page, 7, Sort.by(Sort.Direction.ASC, "foundDate"));
        Page<LostItem> lostItems = lostItemRepository.findAllByVenueId(venueId, pageRequest);


        return lostItems;
    }

    // 사장님용 분실물 상세 조회
    @Override
    public LostItemResponseDTO.LostItemDetailDTO getLostItemDetail(Long lostItemId, Member member, Long venueId) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.VENUE_NOT_FOUND));

        LostItem lostItem = lostItemRepository.findById(lostItemId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LostItem_NOT_FOUND));

        validateOwner(member, venue);

        if (!lostItem.getVenue().equals(venue)) {
            throw new GeneralException(ErrorStatus.LOST_ITEM_VENUE_MISMATCH);
        }

        return LostItemConverter.lostItemDetailDTO(lostItem);
    }

    @Override
    public Page<LostItem> searchLostItems(Integer page, Member member, Long venueId, String keyword) {

        Pageable pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.ASC, "foundDate"));
        Page<LostItem> lostItems;

        if (keyword == null || keyword.trim().isEmpty()) {
            // 검색어가 없을 경우 전체 목록 조회
            lostItems = lostItemRepository.findAllByVenueId(venueId, pageRequest);
        } else {
            lostItems = lostItemRepository.findAllByVenueIdAndTitleContainingOrDescriptionContaining(
                    venueId, keyword, keyword, pageRequest);
        }

        if (lostItems.isEmpty()) {
            throw new GeneralException(ErrorStatus.LOST_ITEM_SEARCH_NOT_FOUND);
        }

        return lostItems;
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
