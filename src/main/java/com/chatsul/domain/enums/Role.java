package com.chatsul.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	OWNER("사장"),
	USER("유저"),
	TEMP("임시 소셜로그인유저");

	private final String description;
}
