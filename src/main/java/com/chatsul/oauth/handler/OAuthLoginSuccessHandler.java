package com.chatsul.oauth.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.chatsul.domain.Member;
import com.chatsul.jwt.principal.PrincipalDetails;
import com.chatsul.util.CookieUtil;
import com.chatsul.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Value("${Jwt.redirect}")
	private String REDIRECT_URI;

	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;

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

		// 액세스 토큰을 담아 리다이렉트
		String redirectUri = String.format(REDIRECT_URI, accessToken, member.getRole());
		getRedirectStrategy().sendRedirect(request, response, redirectUri);
	}
}
