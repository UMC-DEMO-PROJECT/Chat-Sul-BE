package com.chatsul.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chatsul.domain.LostItem;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {

	Page<LostItem> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Modifying
	@Query("DELETE FROM LostItem l WHERE l.lostItemId = :id")
	void deleteByLostItemId(@Param("id") Long id);
}
