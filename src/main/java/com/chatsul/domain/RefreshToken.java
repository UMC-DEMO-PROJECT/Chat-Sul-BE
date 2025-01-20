package com.chatsul.domain;

import com.chatsul.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
// @RedisHash(value = "refresh_token")
public class RefreshToken extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 60)
	private String email;
	private String refreshToken;

	public RefreshToken(String email, String refreshToken) {
		this.email = email;
		this.refreshToken = refreshToken;
	}
}
