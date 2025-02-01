package com.chatsul.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatsul.domain.Uuid;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
