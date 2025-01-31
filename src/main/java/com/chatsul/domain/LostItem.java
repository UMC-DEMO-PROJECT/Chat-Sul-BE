package com.chatsul.domain;

import java.time.LocalDate;

import com.chatsul.domain.common.BaseEntity;
import com.chatsul.domain.enums.LostItemStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LostItem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lostItemId;

	private String title;

	private LocalDate foundDate;

	private String description;

	private String itemImg;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	private LostItemStatus lostItemStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venue_id")
	private Venue venue;

	public void updateStatus() {
		this.lostItemStatus = LostItemStatus.FOUND;
	}
	public void updateTitle(String title) { this.title = title; }
	public void updateDescription(String description) { this.description = description; }
	public void updateItemImg(String itemImg) { this.itemImg = itemImg; }
	public void updateFoundDate(LocalDate foundDate) { this.foundDate = foundDate; }
}
