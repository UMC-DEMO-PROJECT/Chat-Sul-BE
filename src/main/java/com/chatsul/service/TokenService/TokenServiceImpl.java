package com.chatsul.service.TokenService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.TokenConverter;
import com.chatsul.domain.RefreshToken;
import com.chatsul.repository.RefreshTokenRepository;
import com.chatsul.util.CookieUtil;
import com.chatsul.util.JwtUtil;
import com.chatsul.web.dto.TokenResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;

	// 리프레쉬 토큰을 재발행하는 메서드
	@Override
	public TokenResponseDTO.TokenDTO reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = cookieUtil.getCookie(request);
		String refreshToken = cookie.getValue();
		String email = jwtUtil.getEmailFromToken(refreshToken);
		RefreshToken existRefreshToken = refreshTokenRepository.findByEmail(email)
			.orElseThrow(() -> new GeneralException(ErrorStatus.REFRESH_TOKEN_NOT_FOUND));

		String newAccessToken;
		if (!existRefreshToken.getRefreshToken().equals(refreshToken) || jwtUtil.isTokenExpired(refreshToken)) {
			// 리프레쉬 토큰이 다르거나, 만료된 경우, 재로그인 필요
			throw new GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN);
		} else {
			// 액세스 토큰 재발급
			newAccessToken = jwtUtil.generateAccessToken(email);
		}

		// 리프레쉬 토큰이 담긴 쿠키 생성 후 설정
		Cookie newCookie = cookieUtil.createCookie(email);
		response.addCookie(newCookie);

		// 새로운 리프레쉬 토큰 저장
		RefreshToken newRefreshToken = new RefreshToken(email, newCookie.getValue());
		refreshTokenRepository.save(newRefreshToken);

		// 새로운 액세스 토큰을 담아 반환
		return TokenConverter.toTokenDTO(newAccessToken);
	}
}
