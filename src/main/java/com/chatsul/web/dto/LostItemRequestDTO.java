package com.chatsul.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LostItemRequestDTO {
    private Long lostItemId;
    private String title;
    private String itemImg;
    private String description;

    @Builder
    public LostItemRequestDTO(Long lostItemId, String title, String description, String itemImg) {
        this.lostItemId = lostItemId;
        this.title = title;
        this.description = description;
        this.itemImg = itemImg;
    }
}
