package com.hansung.leafly.domain.bookreview.repository;

import com.hansung.leafly.domain.bookreview.entity.BookReview;
import com.hansung.leafly.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    List<BookReview> findAllByMember(Member member);
}
