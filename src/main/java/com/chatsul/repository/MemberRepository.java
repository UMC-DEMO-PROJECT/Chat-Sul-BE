package com.chatsul.repository;

import java.util.Optional;

import com.chatsul.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
	Optional<Member> findByProviderAndProviderId(String provider, String providerId);

	@Query("SELECT m FROM Member m LEFT JOIN FETCH m.venues WHERE m.id = :memberId")
	Optional<Member> findByIdWithVenues(@Param("memberId") Long memberId);
}
