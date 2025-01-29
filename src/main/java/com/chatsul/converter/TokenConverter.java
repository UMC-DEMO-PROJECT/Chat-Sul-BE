package com.chatsul.converter;

import com.chatsul.web.dto.TokenResponseDTO;

public class TokenConverter {
	public static TokenResponseDTO.TokenDTO toTokenDTO(String accessToken) {
		return TokenResponseDTO.TokenDTO.builder()
			.accessToken(accessToken)
			.build();
	}
}
