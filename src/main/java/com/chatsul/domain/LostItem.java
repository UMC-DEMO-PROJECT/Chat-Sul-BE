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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LostItem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lostItemId;

	private String title;

	private LocalDate foundDate;

	private String description;

	private String itemImg;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	@Column(name = "lost_item_status", nullable = false)
	private LostItemStatus lostItemStatus = LostItemStatus.Lost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venue_id")
	private Venue venue;

	public void updateStatus() {
		this.lostItemStatus = lostItemStatus.Found;
	}

	@Builder
	public LostItem(String title, LocalDate foundDate,
		String description, String itemImg, LostItemStatus lostItemStatus, Venue venue) {
		this.title = title;
		this.foundDate = foundDate;
		this.description = description;
		this.itemImg = itemImg;
		this.lostItemStatus = lostItemStatus;
		this.venue = venue;
	}

	public LostItemStatus getlostItemStatus() {
		return lostItemStatus;
	}
}
