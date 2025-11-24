package com.hansung.leafly.domain.member.repository;

import com.hansung.leafly.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    @Query("""
        select m from Member m
        left join fetch m.libraries
        left join fetch m.bookmarks
        where m.id = :memberId
    """)
    Optional<Member> findDetailById(Long memberId);
}
