package com.chatsul.web.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

public class LostItemRequestDTO {

	@Getter
	@Setter
	public static class RegisterLostItemRequestDTO {
		private String title;
		private List<MultipartFile> itemImg;
		private String description;
	}

	@Getter
	@Setter
	public static class UpdateLostItemRequestDTO {
		private String title;
		private List<MultipartFile> itemImg;
		private String description;
		private LocalDate foundDate;
	}
}
