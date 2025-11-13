package com.hansung.leafly.domain.member.repository;

import com.hansung.leafly.domain.member.entity.Genre;
import com.hansung.leafly.domain.member.entity.enums.GenreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findAllByTypeIn(List<GenreType> types);
}
