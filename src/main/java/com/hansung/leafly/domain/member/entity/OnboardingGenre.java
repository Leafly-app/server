package com.hansung.leafly.domain.member.entity;

import com.hansung.leafly.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ONBOARDING_GENRE")
public class OnboardingGenre extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ OnboardingInfo와 다:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id")
    private Onboarding onboarding;

    // ✅ Genre와 다:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

}
