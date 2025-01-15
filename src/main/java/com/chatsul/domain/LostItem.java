package com.chatsul.domain;

import java.time.LocalDate;

import com.chatsul.domain.common.BaseEntity;
import com.chatsul.domain.enums.LostItemStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LostItem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lostItemId;

	private String title;

	private LocalDate foundDate;

	private String description;

	private String itemImg;

	@Enumerated(EnumType.STRING)
	@Column(name = "lost_item_status", nullable = false)
	private LostItemStatus lostItemStatus = LostItemStatus.Lost;

	public void updateStatus() {
		this.lostItemStatus = lostItemStatus.Found;
	}

	@Builder
	public LostItem(String title, LocalDate foundDate,
		String description, String itemImg) {
		this.title = title;
		this.foundDate = foundDate;
		this.description = description;
		this.itemImg = itemImg;
	}
	
	public Long getLostItemId() {
		return lostItemId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getFoundDate() {
		return foundDate;
	}

	public String getItemImg() {
		return itemImg;
	}

	public LostItemStatus getlostItemStatus() {
		return lostItemStatus;
	}

}
