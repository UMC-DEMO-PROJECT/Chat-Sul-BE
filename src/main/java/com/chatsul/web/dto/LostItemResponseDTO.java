package com.chatsul.web.dto;

import com.chatsul.domain.LostItem;
import com.chatsul.domain.enums.LostItemStatus;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LostItemResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LostItemDTO { //LostItem을 LostItemDTO로 변환
        private Long lostItemId;
        private String title;
        private LocalDate foundDate;
        private String lostItemStatus;

        public static LostItemDTO from(LostItem entity) {
            return LostItemDTO.builder()
                    .lostItemId(entity.getLostItemId())
                    .title(entity.getTitle())
                    .foundDate(entity.getFoundDate())
                    .lostItemStatus(entity.getlostItemStatus() == LostItemStatus.Lost ? "미수취" : "완료")
                    .build();
        }
    }


    @Getter
    @Builder
    public static class LostItemDetail {  //상세 조회
        private Long lostItemId;
        private String title;
        private LocalDate foundDate;
        private String lostItemStatus;
        private String itemImg;
        private String description;

        public static LostItemDetail toDetailItem(LostItem entity) {

            String status;
            if (entity.getlostItemStatus() == null) {
                status = "미수취";
            } else {
                status = entity.getlostItemStatus().toString().equals("Lost") ? "미수취" : "완료";
            }

            return LostItemDetail.builder()
                    .lostItemId(entity.getLostItemId())
                    .title(entity.getTitle())
                    .foundDate(entity.getFoundDate())
                    .lostItemStatus((entity.getlostItemStatus() == LostItemStatus.Lost ? "미수취" : "완료"))
                    .itemImg(entity.getItemImg())
                    .description(entity.getDescription())
                    .build();
        }
    }

    private List<LostItemDTO> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;

    public static LostItemResponseDTO LostItemList(Page<LostItem> page) {


        return LostItemResponseDTO.builder()
                .content(page.getContent().stream()
                        .map(LostItemDTO::from)
                        .collect(Collectors.toList()))
                .currentPage(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .hasNext(page.hasNext())
                .build();
    }

    public static LostItemDetail detail(LostItem entity) {
        return LostItemDetail.toDetailItem(entity);
    }

}
