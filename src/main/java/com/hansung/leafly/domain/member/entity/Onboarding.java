package com.hansung.leafly.domain.member.entity;

import com.hansung.leafly.domain.member.entity.enums.Gender;
import com.hansung.leafly.domain.member.entity.Genre;
import com.hansung.leafly.domain.member.entity.enums.ReadingFrequency;
import com.hansung.leafly.domain.member.entity.enums.ReadingPurpose;
import com.hansung.leafly.domain.member.web.dto.OnboardingReq;
import com.hansung.leafly.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ONBOARDINGS")
public class Onboarding extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "birth_year")
    private String birthYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    // 독서 목적
    @Enumerated(EnumType.STRING)
    @Column(name = "reading_purpose")
    private ReadingPurpose readingPurpose;

    // 독서 빈도
    @Enumerated(EnumType.STRING)
    @Column(name = "reading_frequency")
    private ReadingFrequency readingFrequency;

    // 관심 장르 연결
    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OnboardingGenre> favoriteGenres = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    public static Onboarding from(Member member, OnboardingReq req){
        return Onboarding.builder()
                .member(member)
                .birthYear(req.getBirthYear())
                .gender(req.getGender())
                .readingPurpose(req.getReadingPurpose())
                .readingFrequency(req.getReadingFrequency())
                .build();
    }

    public void addGenre(Genre genre) {
        OnboardingGenre join = OnboardingGenre.builder()
                .onboarding(this)
                .genre(genre)
                .build();
        favoriteGenres.add(join);
    }
}
