package com.chatsul.domain;

import com.chatsul.domain.common.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Venue extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 가게 이름
	@Column(nullable = false, length = 50)
	private String name;

	// 가게 주소
	@Column(nullable = false, length = 100)
	private String address;

	// 가게 전화번호
	@Column(nullable = false, length = 50)
	private String phone;

	// 가게 위도
	@Column(nullable = false)
	private Double latitude;

	// 가게 경도
	@Column(nullable = false)
	private Double longitude;

	// 가게 계좌
	@Column(nullable = false, length = 50)
	private String account;

	// 가게 은행
	@Column(nullable = false, length = 50)
	private String bank;

	@OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
	private List<Reservation> reservationList = new ArrayList<>();
}
