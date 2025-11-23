package com.hansung.leafly.domain.book.service;

import com.hansung.leafly.domain.book.entity.enums.BookGenre;
import com.hansung.leafly.domain.book.exception.BookNotFoundException;
import com.hansung.leafly.domain.book.web.dto.*;
import com.hansung.leafly.infra.aladin.AladinClient;
import com.hansung.leafly.infra.aladin.dto.BookRes;
import com.hansung.leafly.infra.openai.OpenAiClient;
import com.hansung.leafly.infra.openai.dto.BookSummaryAiRes;
import com.hansung.leafly.infra.openai.prompt.RecommendationPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if (bookRes == null ||
                bookRes.getItems() == null ||
                bookRes.getItems().isEmpty() ||
                bookRes.getItems().get(0) == null) {
            throw new BookNotFoundException();
        }

        BookDetailRes detail = BookDetailRes.from(bookRes);

        // BookSummaryPrompt 생성 → AI 요약 + 태그 생성
        String prompt = RecommendationPrompt.build(detail);
        BookSummaryAiRes summaryAi = openAiClient.summarizeBook(prompt);

        // 해당 카테고리 기반 추천 목록 검색
        String categoryId = String.valueOf(bookRes.getItems().get(0).getCategoryId());
        AladinSearchRes categorySearchRes = aladinClient.searchByCategory(categoryId);
        List<RecommendRes> recommendations = toRecommendationList(categorySearchRes);

        //boolean liked = likeService.isBookLiked(memberId, isbn13);

        return BookInfoRes.of(
                detail,
                summaryAi.summary(),
                summaryAi.tags(),
                recommendations,
                false
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
