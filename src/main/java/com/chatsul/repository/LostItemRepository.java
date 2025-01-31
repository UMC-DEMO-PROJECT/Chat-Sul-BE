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

	Page<LostItem> findAllByVenueId(Long venueId, Pageable pageable);
	Page<LostItem> findAllByVenueIdAndTitleContainingOrDescriptionContaining
			(Long venueId, String titleKeyword, String desKeyword, Pageable pageable);
	// 실제로는 한 키워드로 타이틀과 설명 모두 검색
}
