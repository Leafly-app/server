package com.hansung.leafly.infra.openai.prompt;

import com.hansung.leafly.domain.member.entity.Onboarding;

public class RecommendationPrompt {
    public static String build(Onboarding onboarding) {
        return """
        당신은 전문 한국 도서 큐레이터입니다.
        아래 사용자의 온보딩 정보를 바탕으로, 그 사람에게 적합한 **실제로 존재하는 도서 10권**을 추천해주세요.
        1. **한국 도서 우선 추천**
        - 한국 저자가 쓴 도서 또는 한국에서 출간된 번역서/한국판을 가장 우선적으로 추천하세요.
        - 가능하면 10권 중 **최소 7권 이상은 한국 도서**가 되도록 하세요.
                
        2. **외국 도서도 허용**
        - 사용자의 취향에 매우 잘 맞는 외국 도서는 일부 포함해도 됩니다.
        - 하지만 전체의 소수만 포함해주세요.
                
        3. **각 도서에는 다음 두 필드만 포함**
        - title: 책 제목 (한국어 제목)
        - reason: 그 책이 왜 이 사용자에게 적합한지 한 줄 설명

        [사용자 온보딩 정보]
        - 성별: %s
        - 나이대: %s
        - 독서 목적: %s
        - 관심 장르: %s
        - 읽는 빈도: %s

        [응답 형식(JSON)]
        {
          "books": [
            {
              "title": "책 제목",
              "reason": "이 사용자가 왜 이 책을 좋아할지에 대한 간단한 추천 이유"
            }
          ]
        }

        주의사항:
        - 반드시 실제 존재하는 책만 추천하세요.
        - 출력은 반드시 JSON 형식만 유지하세요.
        - books 배열은 정확히 10개의 항목을 포함해야 합니다.
        """.formatted(
                onboarding.getGender(),
                onboarding.getBirthYear(),
                onboarding.getReadingPurpose(),
                onboarding.getFavoriteGenres(),
                onboarding.getReadingFrequency()
        );
    }
}
