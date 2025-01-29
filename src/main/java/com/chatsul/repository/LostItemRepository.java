package com.chatsul.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chatsul.domain.LostItem;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {

	List<LostItem> findTop6ByOrderByCreatedAtDesc();

	Page<LostItem> findAllByOrderByCreatedAtDesc(Pageable pageable);

	void deleteByLostItemId(@Param("id") Long id);
}
