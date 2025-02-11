package com.chatsul.web.dto;

import java.util.List;

import com.chatsul.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponseDTO {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LoginSuccessDTO {
		String accessToken;
		Role role;
		Long venueId;
	}
}
