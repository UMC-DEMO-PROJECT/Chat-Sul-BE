package com.chatsul.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class LostItemRequestDTO {

    @Getter
    public static class RegisterLostItemRequestDTO {
        private String title;
        private String itemImg;
        private String description;
    }
}
