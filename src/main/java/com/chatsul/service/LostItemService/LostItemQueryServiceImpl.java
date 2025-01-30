package com.chatsul.service.LostItemService;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.domain.LostItem;
import com.chatsul.domain.Member;
import com.chatsul.repository.LostItemRepository;
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

    @Override
    public Page<LostItem> getLostItems(Integer page, Member member, Long venueId) {

        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "foundDate"));
        Page<LostItem> lostItems = lostItemRepository.findAllByVenueId(venueId, pageRequest);

        if (lostItems.isEmpty()) {
            throw new GeneralException(ErrorStatus.LostItem_NOT_FOUND);
        }

        return lostItems;
    }

    @Override
    public LostItemResponseDTO.LostItemDetail getLostItemDetail(Long lostItemId) {
        LostItem lostItem = lostItemRepository.findById(lostItemId)
                .orElseThrow(() -> new EntityNotFoundException("분실물을 찾을 수 없습니다."));
        return LostItemResponseDTO.LostItemDetail.toDetailItem(lostItem);
    }
}
