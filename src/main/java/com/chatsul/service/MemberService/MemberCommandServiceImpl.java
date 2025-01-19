package com.chatsul.service.MemberService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.MemberConverter;
import com.chatsul.domain.Member;
import com.chatsul.domain.enums.Role;
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
}
