package com.chatsul.service;

import com.chatsul.converter.LostItemConverter;
import com.chatsul.domain.LostItem;
import com.chatsul.domain.Venue;
import com.chatsul.repository.LostItemRepository;
import com.chatsul.repository.VenueRepository;
import com.chatsul.web.dto.LostItemRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LostItemCommandServiceImpl implements LostItemCommandService {
    private final LostItemRepository lostItemRepository;
    private final LostItemConverter lostItemConverter;
    private final VenueRepository venueRepository;

    @Override
    public String saveLostItem(LostItemRequestDTO requestDTO) {
        Venue venue = venueRepository.findById(requestDTO.getVenueId())
                .orElseThrow(() -> new EntityNotFoundException("매장을 찾을 수 없습니다."));

        LostItem lostItem = lostItemConverter.toEntity(requestDTO, venue);
        lostItemRepository.save(lostItem);
        return "분실물이 등록되었습니다.";
    }

    @Override
    public void deleteLostItem(LostItemRequestDTO requestDTO) {
        log.info("Deleting lost item with ID: {}", requestDTO.getLostItemId());

        if (!lostItemRepository.existsById(requestDTO.getLostItemId())) {
            throw new EntityNotFoundException("분실물을 찾을 수 없습니다.");
        }

        lostItemRepository.deleteByLostItemId(requestDTO.getLostItemId());
        lostItemRepository.flush();

        log.info("분실물이 삭제되었습니다.");
    }

    @Override
    public String updateLostItemStatus(Long lostItemId) {
        LostItem lostItem = lostItemRepository.findById(lostItemId)
                .orElseThrow(() -> new EntityNotFoundException("분실물을 찾을 수 없습니다."));

        lostItem.updateStatus();
        return "분실물이 수취되었습니다.";
    }
}
