package com.chatsul.converter;

import java.util.ArrayList;
import java.util.List;

import com.chatsul.domain.Member;
import com.chatsul.domain.enums.Role;
import com.chatsul.web.dto.MemberRequestDTO;
import com.chatsul.web.dto.MemberResponseDTO;

public class MemberConverter {

	// 일반 회원가입
	public static Member toMember(MemberRequestDTO.JoinDTO member) {
		return Member.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.name(member.getName())
			.phoneNumber(member.getPhoneNumber())
			.role(Role.USER)
			.reservationList(new ArrayList<>())
			.build();
	}

	// 소셜 회원가입 (추가정보 기입 X)
	public static Member toSocialMember(String provider, String providerId, String password) {
		String email = String.format("%s@%s.com", providerId, provider);
		return Member.builder()
			.email(email)
			.password(password)
			.name(provider)
			.phoneNumber("01011111111")
			.role(Role.TEMP)
			.provider(provider)
			.providerId(providerId)
			.build();
	}

	public static MemberResponseDTO.LoginSuccessDTO toLoginSuccessDTO(String accessToken, Role role,
		List<Long> venueIds) {
		return MemberResponseDTO.LoginSuccessDTO.builder()
			.accessToken(accessToken)
			.role(role)
			.venueId(venueIds.get(0))
			.build();
	}
}
