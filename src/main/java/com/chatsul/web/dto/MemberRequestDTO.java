package com.chatsul.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class MemberRequestDTO {

	// 일반 유저 회원가입용
	@Getter
	public static class JoinDTO {
		@NotBlank
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자 제외 2~10자리여야 합니다.")
		String name;
		@NotBlank
		@Pattern(
			regexp = "^010[0-9]{8}$",
			message = "전화번호는 010으로 시작하며 숫자 8자리로 구성된 11자리여야 합니다."
		)
		String phoneNumber;
		@NotBlank
		@Email
		String email;
		@NotBlank
		@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 영문자, 숫자, 특수기호 1개 이상 포함 8자 이상 15자 이하여야 합니다.")
		String password;
	}
	
	// 소셜 로그인 유저를 위한 추가정보 입력
	@Getter
	public static class JoinSocialDTO {
		@NotBlank
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자 제외 2~10자리여야 합니다.")
		String name;
		@NotBlank
		@Pattern(
			regexp = "^010[0-9]{8}$",
			message = "전화번호는 010으로 시작하며 숫자 8자리로 구성된 11자리여야 합니다."
		)
		String phoneNumber;
	}

	// 로그인 정보
	@Getter
	public static class LoginDTO {
		@NotBlank
		@Email
		private String email;
		@NotBlank
		@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 영문자, 숫자, 특수기호 1개 이상 포함 8자 이상 15자 이하여야 합니다.")
		private String password;
	}
}
