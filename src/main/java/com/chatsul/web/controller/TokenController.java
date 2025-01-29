package com.chatsul.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.service.TokenService.TokenService;
import com.chatsul.web.dto.TokenResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TokenController {

	private final TokenService tokenService;

	// 액세스 토큰을 재발행하는 API
	@GetMapping("/reissue/access-token")
	public ApiResponse<TokenResponseDTO.TokenDTO> reissueAccessToken(HttpServletRequest request,
		HttpServletResponse response) {
		TokenResponseDTO.TokenDTO accessToken = tokenService.reissueAccessToken(request, response);
		return ApiResponse.onSuccess(accessToken);
	}
}
