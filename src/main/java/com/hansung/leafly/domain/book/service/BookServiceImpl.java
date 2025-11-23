package com.hansung.leafly.domain.book.service;

import com.hansung.leafly.domain.book.entity.enums.BookGenre;
import com.hansung.leafly.domain.book.web.dto.*;
import com.hansung.leafly.infra.aladin.AladinClient;
import com.hansung.leafly.infra.aladin.dto.BookRes;
import com.hansung.leafly.infra.openai.OpenAiClient;
import com.hansung.leafly.infra.openai.dto.BookSummaryAiRes;
import com.hansung.leafly.infra.openai.prompt.RecommendationPrompt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AladinClient aladinClient;
    private final OpenAiClient openAiClient;

    // 검색
    @Override
    public List<SearchRes> search(String keyword, BookFilterReq req) {
        AladinSearchRes response = aladinClient.search(keyword);

        if (response == null || response.item() == null) {
            return List.of();
        }

        if (req == null || req.getGenres() == null || req.getGenres().isEmpty()) {
            return response.item().stream()
                    .map(item -> SearchRes.from(item, false))
                    .toList();
        }

        List<BookGenre> filters = req.getGenres();

        return response.item().stream()
                .filter(item -> matchesGenre(item, filters))
                .map(item -> SearchRes.from(item, false))
                .toList();
    }

    @Override
    public BookInfoRes details(Long isbn) {
        // ISBN 기반 상세 정보 조회
        BookRes bookRes = aladinClient.fetchBookByIsbn(String.valueOf(isbn));
        IsbnSearchRes searchRes = IsbnSearchRes.from(bookRes);
        BookDetailRes detail = BookDetailRes.from(searchRes);

        // BookSummaryPrompt 생성 → AI 요약 + 태그 생성
        String prompt = RecommendationPrompt.build(detail);
        BookSummaryAiRes summaryAi = openAiClient.summarizeBook(prompt);

        // categoryName에서 categoryId 추출
        String categoryId = extractCategoryId(detail.categoryName());

        // 해당 카테고리 기반 추천 목록 검색
        AladinSearchRes categorySearchRes = aladinClient.searchByCategory(categoryId);
        List<RecommendRes> recommendations = toRecommendationList(categorySearchRes);

        // 최종 Res 조립
        return BookInfoRes.of(
                detail,
                summaryAi.summary(),
                summaryAi.tags(),
                recommendations
        );
    }

    //카테고리 필터링
    private boolean matchesGenre(AladinBookItem item, List<BookGenre> targetGenres) {
        String middle = extractMiddleCategory(item.categoryName());

        for (BookGenre genre : targetGenres) {
            if (middle.contains(genre.getValue())) {
                return true;
            }
        }
        return false;
    }

    //카테고리 추출 메소드
    private String extractMiddleCategory(String categoryName) {
        if (categoryName == null) return "";
        String[] tokens = categoryName.split(">");
        return tokens.length >= 2 ? tokens[1].trim() : "";
    }

    //카테고리 번호 추출 메소드
    private String extractCategoryId(String categoryName) {
        if (categoryName == null) return null;

        int start = categoryName.indexOf("[");
        int end = categoryName.indexOf("]");

        if (start == -1 || end == -1) return null;

        return categoryName.substring(start + 1, end);
    }

    private List<RecommendRes> toRecommendationList(AladinSearchRes res) {
        return res.item().stream()
                .map(item -> new RecommendRes(
                        item.isbn13(),
                        item.title(),
                        item.author(),
                        item.cover()
                ))
                .toList();
    }
}
