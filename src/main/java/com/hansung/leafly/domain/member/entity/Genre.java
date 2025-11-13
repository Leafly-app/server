package com.hansung.leafly.domain.member.entity;

import com.hansung.leafly.domain.member.entity.enums.GenreType;
import com.hansung.leafly.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GENRE")
public class Genre extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 장르 타입
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private GenreType type;
}
