package com.hansung.leafly.domain.library.repository;

import com.hansung.leafly.domain.library.entity.Library;
import com.hansung.leafly.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    boolean existsByMemberAndIsbn(Member member, String isbn13);

    Optional<Library> findByMemberAndIsbn(Member member, String isbn);
}
