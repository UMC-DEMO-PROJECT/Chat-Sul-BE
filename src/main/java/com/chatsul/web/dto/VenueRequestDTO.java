package com.chatsul.web.dto;

import com.chatsul.domain.enums.Bank;
import lombok.Getter;

@Getter
public class VenueRequestDTO {

	private String name;
	private String address;
	private String phone;
	private String account;
	private Bank bank;
}