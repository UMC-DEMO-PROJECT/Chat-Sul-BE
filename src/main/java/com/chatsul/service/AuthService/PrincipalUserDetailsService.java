package com.chatsul.service.AuthService;

import com.chatsul.jwt.principal.PrincipalDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.domain.Member;
import com.chatsul.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	// 일반 유저 로그인 처리 (이메일)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(username).orElseThrow(() ->
			new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		return new PrincipalDetails(member);
	}
}
