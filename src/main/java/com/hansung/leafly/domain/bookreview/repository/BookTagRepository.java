package com.hansung.leafly.domain.bookreview.repository;

import com.hansung.leafly.domain.bookreview.entity.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTagRepository extends JpaRepository<BookTag, Long> {
}
