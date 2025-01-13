package com.chatsul.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class MemberRequestDTO {

	@Getter
	public static class JoinDTO {
		@NotBlank
		String name;
		@NotBlank
		String phoneNumber;
		@NotBlank
		@Email
		String email;
		@NotBlank
		String password;
	}

	@Getter
	public static class LoginDTO {
		private String email;
		private String password;
	}
}
