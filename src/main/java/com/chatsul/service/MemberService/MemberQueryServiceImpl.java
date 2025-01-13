package com.chatsul.service.MemberService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.TokenConverter;
import com.chatsul.domain.Member;
import com.chatsul.repository.MemberRepository;
import com.chatsul.util.CookieUtil;
import com.chatsul.util.JwtUtil;
import com.chatsul.web.dto.MemberRequestDTO;
import com.chatsul.web.dto.TokenResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;
	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;

	@Override
	public TokenResponseDTO.TokenDTO login(MemberRequestDTO.LoginDTO dto, HttpServletResponse response) {
		Member loginMember = memberRepository.findByEmail(dto.getEmail())
			.filter(m -> encoder.matches(dto.getPassword(), m.getPassword()))
			.orElseThrow(
				() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
			);
		Cookie cookie = cookieUtil.createCookie(dto.getEmail());
		response.addCookie(cookie);
		return TokenConverter.toTokenDTO(
			jwtUtil.generateAccessToken(loginMember.getEmail())
		);
	}
}
