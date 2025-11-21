package com.hansung.leafly.domain.bookreview.repository;

import com.hansung.leafly.domain.bookreview.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
}
