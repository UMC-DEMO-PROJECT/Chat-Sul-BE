package com.chatsul.converter;

import com.chatsul.domain.LostItem;
import com.chatsul.web.dto.LostItemRequestDTO;
import com.chatsul.web.dto.LostItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class LostItemConverter {

    public LostItem toEntity(LostItemRequestDTO dto) {
        return LostItem.builder()
                .title(dto.getTitle())
                .foundDate(LocalDate.now())
                .description(dto.getDescription())
                .itemImg(dto.getItemImg())
                .build();
    }

    public LostItemResponseDTO toDto(Page<LostItem> lostItems) {
        return LostItemResponseDTO.builder()
                .content(lostItems.getContent().stream()
                        .map(LostItemResponseDTO.LostItemDTO::from)
                        .collect(Collectors.toList()))
                .currentPage(lostItems.getNumber() + 1)
                .totalPages(lostItems.getTotalPages())
                .totalElements(lostItems.getTotalElements())
                .hasNext(lostItems.hasNext())
                .build();
    }
}
