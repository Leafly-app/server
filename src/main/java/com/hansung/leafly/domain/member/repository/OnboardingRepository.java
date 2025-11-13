package com.hansung.leafly.domain.member.repository;

import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.entity.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingRepository extends JpaRepository<Onboarding, Integer> {
    boolean existsByMember(Member member);
}
