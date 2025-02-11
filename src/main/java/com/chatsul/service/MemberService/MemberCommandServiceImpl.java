package com.chatsul.service.MemberService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.MemberConverter;
import com.chatsul.domain.Member;
import com.chatsul.domain.enums.Role;
import com.chatsul.repository.MemberRepository;
import com.chatsul.util.CookieUtil;
import com.chatsul.util.JwtUtil;
import com.chatsul.web.dto.MemberRequestDTO;
import com.chatsul.web.dto.MemberResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;

	@Override
	public Member joinMember(MemberRequestDTO.JoinDTO request) {
		if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new GeneralException(ErrorStatus.MEMBER_EXIST);
		}
		Member newMember = MemberConverter.toMember(request);
		newMember.encodePassword(passwordEncoder.encode(request.getPassword()));
		return memberRepository.save(newMember);
	}

	@Override
	public Member joinSoicalMember(MemberRequestDTO.JoinSocialDTO request, Member member) {
		// 추가 정보 기입 대상이 아닌 경우
		if (member.getRole() != Role.TEMP) {
			throw new GeneralException(ErrorStatus.MEMBER_ROLE_INVALID);
		}
		member.updateRoleTempToUser(request.getName(), request.getPhoneNumber());
		return null;
	}

	@Override
	public MemberResponseDTO.LoginSuccessDTO login(MemberRequestDTO.LoginDTO dto, HttpServletResponse response) {
		Member loginMember = memberRepository.findByEmail(dto.getEmail())
			.filter(m -> passwordEncoder.matches(dto.getPassword(), m.getPassword()))
			.orElseThrow(
				() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
			);

		String accessToken = jwtUtil.generateAccessToken(loginMember.getEmail());
		setRefreshToken(dto.getEmail(), response);

		Long venueId = getVenueIdIfMemberIsOwner(loginMember);

		return MemberConverter.toLoginSuccessDTO(accessToken, loginMember.getRole(), venueId);
	}

	private void setRefreshToken(String email, HttpServletResponse response) {
		// 새 RefreshToken 발급
		String refreshToken = jwtUtil.generateRefreshToken(email);

		// response에 cookie로 반환
		Cookie cookie = cookieUtil.createCookie(refreshToken);
		response.addCookie(cookie);
	}

	// 사용자가 사장이라면 매장 Id 반환
	private static Long getVenueIdIfMemberIsOwner(Member loginMember) {
		if (!loginMember.isOwner()) {
			return null;
		}
		return loginMember.getVenue().getId();
	}
}
