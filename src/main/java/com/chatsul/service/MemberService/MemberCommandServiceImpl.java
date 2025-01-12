package com.chatsul.service.MemberService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatsul.converter.MemberConverter;
import com.chatsul.domain.Member;
import com.chatsul.repository.MemberRepository;
import com.chatsul.web.dto.MemberRequestDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Member joinMember(MemberRequestDTO.JoinDTO request) {
		Member newMember = MemberConverter.toMember(request);
		newMember.encodePassword(passwordEncoder.encode(request.getPassword()));
		return memberRepository.save(newMember);
	}
}
