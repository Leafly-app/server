package com.hansung.leafly.infra.openai.prompt;

import com.hansung.leafly.domain.book.web.dto.BookDetailRes;
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

    public static String build(BookDetailRes book) {
        return """
        당신은 한국 도서 전문 리뷰어이자 요약 전문가입니다.
        아래 책 정보를 기반으로 **정확하고 신뢰할 수 있는 요약(최소 2~3문장)**과  
        **책의 핵심 주제나 톤을 반영하는 태그 최소 2~5개**를 생성해주세요.

        [책 정보]
        - 제목: %s
        - 저자: %s
        - 출판사: %s
        - 출판일: %s
        - 카테고리: %s
        - 책 설명: %s

        [요약 작성 기준]
        1. 줄거리를 요약하되 스포일러는 포함하지 않습니다.
        2. 책의 핵심 메시지·감정·분위기·주제를 자연스럽게 드러냅니다.
        3. 문장은 최소 2~3줄 분량으로 작성합니다.  
           (너무 짧은 한줄 요약 금지)
        
        [태그 작성 기준]
        - #태그 형식으로 작성하세요.
        - 최소 2개 이상, 최대 5개 생성하세요.
        - 책의 성향/주제/톤/분위기/대상 독자 등을 나타내는 단어로 구성하세요.
        - 예시: #우정 #감정 #청소년문학 #소설 #철학 #성장 #심리

        [응답 형식(JSON)]
        {
          "summary": "여기에 책 요약(최소 2~3문장)",
          "tags": ["#태그1", "#태그2", "#태그3"]
        }

        주의사항:
        - 반드시 실제 책 설명을 기반으로 작성하세요.
        - 출력은 반드시 JSON 형식만 유지하세요.
        - 설명이 부족하면 일반적인 출판사 소개 문체로 자연스럽게 보완하세요.
        """.formatted(
                book.title(),
                book.author(),
                book.publisher(),
                book.pubDate(),
                book.categoryName(),
                book.description()
        );
    }
}
