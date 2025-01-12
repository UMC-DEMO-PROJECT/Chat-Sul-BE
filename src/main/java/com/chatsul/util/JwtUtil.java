package com.chatsul.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.domain.Member;
import com.chatsul.repository.MemberRepository;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	private final MemberRepository memberRepository;

	private SecretKey secretKey;
	private long accessTokenExpirationTime;
	private long refreshTokenExpirationTime;

	public JwtUtil(MemberRepository memberRepository, @Value("${Jwt.secret}") String secret,
		@Value("${Jwt.access-token.expiration-time}") long accessExpiration,
		@Value("${Jwt.refresh-token.expiration-time}") long refreshExpiration) {
		this.memberRepository = memberRepository;
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.accessTokenExpirationTime = accessExpiration; // Access 토큰 만료 시간 설정
		this.refreshTokenExpirationTime = refreshExpiration; // Refresh 토큰 만료 시간 설정
	}

	// 액세스 토큰을 발급하는 메서드
	public String generateAccessToken(String email) {
		return Jwts.builder()
			.claim("email", email)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
			.signWith(secretKey)
			.compact();
	}

	// 리프레쉬 토큰을 발급하는 메서드
	public String generateRefreshToken(String email) {
		return Jwts.builder()
			.claim("email", email)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
			.signWith(secretKey)
			.compact();
	}

	// 응답 헤더에서 액세스 토큰을 반환하는 메서드
	public String getAccessTokenFromHeader(String header) {
		if (header == null || !header.startsWith("Bearer ")) {
			return null;
		}
		return header.split(" ")[1];
	}

	// 토큰에서 유저 id를 반환하는 메서드
	public String getEmailFromToken(String token) {
		try {
			return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("email", String.class);
		} catch (JwtException | IllegalArgumentException e) {
			// 토큰이 유효하지 않은 경우
			throw new GeneralException(ErrorStatus.INVALID_TOKEN);
		}
	}

	// 토큰에서 멤버를 반환하는 메서드
	public Member getMemberFromHeader(String authorizationHeader) {
		String token = getAccessTokenFromHeader(authorizationHeader);
		String email = getEmailFromToken(token);

		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
	}

	// Jwt 토큰의 유효기간을 확인하는 메서드
	public boolean isTokenExpired(String token) {
		try {
			Date expirationDate = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration();
			return expirationDate.before(new Date());
		} catch (JwtException | IllegalArgumentException e) {
			// 토큰이 유효하지 않은 경우
			throw new GeneralException(ErrorStatus.EXPIRED_TOKEN);
		}
	}
}
