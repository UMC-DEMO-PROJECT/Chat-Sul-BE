package com.chatsul.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LostItemStatus {
    LOST("미수취"),
    FOUND("완료");

    private final String status;
}
