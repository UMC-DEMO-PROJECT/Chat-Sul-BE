package com.chatsul.service.MemberService;

import com.chatsul.web.dto.MemberRequestDTO;
import com.chatsul.web.dto.TokenResponseDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface MemberQueryService {
	TokenResponseDTO.TokenDTO login(MemberRequestDTO.LoginDTO dto, HttpServletResponse response);
}
