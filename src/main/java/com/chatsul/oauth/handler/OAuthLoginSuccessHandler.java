package com.chatsul.oauth.handler;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.domain.Member;
import com.chatsul.domain.Venue;
import com.chatsul.domain.enums.Role;
import com.chatsul.jwt.principal.PrincipalDetails;
import com.chatsul.repository.MemberRepository;
import com.chatsul.util.CookieUtil;
import com.chatsul.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Value("${Jwt.redirect}")
	private String REDIRECT_URI;

	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;
	private final MemberRepository memberRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		Member member = principalDetails.getMember();

		// 리프레시 토큰 발급
		String refreshToken = jwtUtil.generateRefreshToken(member.getEmail());

		// response에 cookie로 반환
		Cookie cookie = cookieUtil.createCookie(refreshToken);
		response.addCookie(cookie);

		// 액세스 토큰 발급
		String accessToken = jwtUtil.generateAccessToken(member.getEmail());

		// venues 로딩용
		Member fullMember = memberRepository.findByIdWithVenues(member.getId())
			.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		// 액세스 토큰, role, venueId를 담아 리다이렉트
		String redirectUri = setRedirectUri(accessToken, fullMember);

		getRedirectStrategy().sendRedirect(request, response, redirectUri);
	}

	private String setRedirectUri(String accessToken, Member member) {
		String redirectUri = String.format(REDIRECT_URI, accessToken, member.getRole());
		redirectUri = addVenueIdsIfMemberIsOwner(member, redirectUri);
		return redirectUri;
	}

	private String addVenueIdsIfMemberIsOwner(Member member, String redirectUri) {
		if (member.isOwner()) {
			// 인당 사업자 등록 1번만 가능하도록 임시 조치
			Long venueId = member.getVenues().get(0).getId();
			redirectUri = redirectUri + "&venueId=" + venueId;
		}
		return redirectUri;
	}
}
