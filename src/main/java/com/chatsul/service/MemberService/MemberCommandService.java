package com.chatsul.service.MemberService;

import com.chatsul.domain.Member;
import com.chatsul.web.dto.MemberRequestDTO;
import com.chatsul.web.dto.MemberResponseDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface MemberCommandService {
	Member joinMember(MemberRequestDTO.JoinDTO request);
	Member joinSoicalMember(MemberRequestDTO.JoinSocialDTO request, Member member);
	MemberResponseDTO.LoginSuccessDTO login(MemberRequestDTO.LoginDTO dto, HttpServletResponse response);
}
