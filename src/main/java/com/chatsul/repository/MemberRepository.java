package com.chatsul.repository;

import java.util.Optional;

import com.chatsul.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
	Optional<Member> findByProviderAndProviderId(String provider, String providerId);
}
