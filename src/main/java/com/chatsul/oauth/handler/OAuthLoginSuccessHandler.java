package com.chatsul.oauth.handler;

import com.chatsul.domain.Member;
import com.chatsul.domain.RefreshToken;
import com.chatsul.jwt.principal.PrincipalDetails;
import com.chatsul.repository.RefreshTokenRepository;
import com.chatsul.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${Jwt.redirect}")
    private String REDIRECT_URI;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member member = principalDetails.getMember();

        // 기존 리프레시 토큰 삭제
        refreshTokenRepository.deleteByEmail(member.getEmail());

        // 새 리프레시 토큰 발급 후 저장
        String refreshToken = jwtProvider.generateRefreshToken(member.getEmail());
        refreshTokenRepository.save(new RefreshToken(member.getEmail(), refreshToken));

        // 액세스 토큰 발급
        String accessToken = jwtProvider.generateAccessToken(member.getEmail());

        // 액세스 토큰을 담아 리다이렉트
        String redirectUri = String.format(REDIRECT_URI, accessToken);
        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }
}
