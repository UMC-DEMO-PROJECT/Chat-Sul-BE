package com.chatsul.service.TokenService;

import com.chatsul.web.dto.TokenResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {
	TokenResponseDTO.TokenDTO reissueAccessToken(HttpServletRequest request, HttpServletResponse response);
}
