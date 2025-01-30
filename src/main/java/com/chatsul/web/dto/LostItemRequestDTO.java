package com.chatsul.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LostItemRequestDTO {
    private Long lostItemId;
    private String title;
    private String itemImg;
    private String description;
    private Long venueId;
}
