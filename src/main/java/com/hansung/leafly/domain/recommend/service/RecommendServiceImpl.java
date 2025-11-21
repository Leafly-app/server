package com.hansung.leafly.domain.recommend.service;

import com.hansung.leafly.domain.book.web.dto.AladinSearchResponse;
import com.hansung.leafly.domain.book.web.dto.SearchRes;
import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.entity.Onboarding;
import com.hansung.leafly.domain.member.repository.OnboardingRepository;
import com.hansung.leafly.domain.recommend.web.dto.RecommendRes;
import com.hansung.leafly.infra.aladin.AladinClient;
import com.hansung.leafly.infra.openai.OpenAiClient;
import com.hansung.leafly.infra.openai.dto.RecommendAiRes;
import com.hansung.leafly.infra.openai.prompt.RecommendationPrompt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hansung.leafly.domain.member.exception.OnboardingNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendServiceImpl implements RecommendService {
    private final OnboardingRepository onboardingRepository;
    private final OpenAiClient openAiClient;
    private final AladinClient aladinClient;

    //온보딩 기반 책 추천
    @Override
    public List<RecommendRes> recommendations(Member member) {
        //온보딩 조회
        Onboarding onboarding = onboardingRepository.findByMember(member)
                .orElseThrow(OnboardingNotFoundException::new);

        //OpenAi 기반 책 추천
        String prompt = RecommendationPrompt.build(onboarding);
        RecommendAiRes aiRes = openAiClient.recommendBooks(prompt);

        List<RecommendRes> result = new ArrayList<>();

        //실제 존재하는 책 필터링
        for (var book : aiRes.books()) {
            AladinSearchResponse searchRes = aladinClient.search(book.title());

            if (searchRes == null || searchRes.item() == null || searchRes.item().isEmpty()) {
                continue;
            }
            SearchRes details = SearchRes.from(searchRes.item().get(0), false);

            RecommendRes recommendRes = new RecommendRes(
                    book.reason(),
                    details
            );

            result.add(recommendRes);
        }

        return result;
    }
}
