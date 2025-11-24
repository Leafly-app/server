package com.hansung.leafly.domain.bookmark.repository;

import com.hansung.leafly.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    Optional<Bookmark> findByMember_IdAndIsbn(Long memberId, Long isbn);

    @Query("select b.isbn from Bookmark b where b.member.id = :memberId")
    List<Long> findIsbnsByMemberId(@Param("memberId") Long memberId);

    boolean existsByMember_IdAndIsbn(Long memberId, Long isbn);
}
