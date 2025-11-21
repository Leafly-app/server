package com.hansung.leafly.domain.bookreview.repository;

import com.hansung.leafly.domain.bookreview.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
