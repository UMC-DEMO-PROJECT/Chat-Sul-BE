package com.chatsul.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LostItemStatus {
    Lost("미수취"),
    Found("완료");

    private final String status;
}
