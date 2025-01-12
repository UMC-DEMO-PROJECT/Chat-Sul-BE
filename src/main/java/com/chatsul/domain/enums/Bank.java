package com.chatsul.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bank {
	KAKAO_BANK("카카오뱅크"),
	SHINHAN_BANK("신한은행"),
	KB_BANK("국민은행"),
	WOORI_BANK("우리은행"),
	HANA_BANK("하나은행"),
	NH_BANK("농협은행"),
	IBK_BANK("기업은행"),
	SC_BANK("SC제일은행"),
	CITI_BANK("씨티은행");

	private final String bankName;
}
