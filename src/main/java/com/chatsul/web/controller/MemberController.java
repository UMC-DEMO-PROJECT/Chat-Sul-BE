package com.chatsul.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatsul.annotation.CurrentMember;
import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.domain.Member;
import com.chatsul.service.MemberService.MemberCommandService;
import com.chatsul.service.MemberService.MemberQueryService;
import com.chatsul.web.dto.MemberRequestDTO;
import com.chatsul.web.dto.TokenResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberCommandService memberCommandService;
	private final MemberQueryService memberQueryService;

	@PostMapping("/signup")
	@Operation(summary = "회원가입 API")
	public ApiResponse<String> signUp(@RequestBody @Valid MemberRequestDTO.JoinDTO dto) {
		memberCommandService.joinMember(dto);
		return ApiResponse.onSuccess("ok");
	}

	@PostMapping("/login")
	@Operation(summary = "로그인 API")
	public ApiResponse<TokenResponseDTO.TokenDTO> login(@RequestBody MemberRequestDTO.LoginDTO dto,
		HttpServletResponse response) {
		return ApiResponse.onSuccess(memberQueryService.login(dto, response));
	}

}
