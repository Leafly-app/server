package com.hansung.leafly.domain.book.web.dto;

import java.util.List;

public record BookInfoRes(
        BookDetailRes bookDetail,     // 책 상세 정보
        String aiSummary,             // AI가 생성한 요약 문장
        List<String> aiTags,          // AI가 선정한 태그들
        List<RecommendRes> recommendations, // 추천 책 리스트
        boolean isLiked
){
    public static BookInfoRes of(
        BookDetailRes bookDetailRes,
        String aiSummary,
        List<String> aiTags,
        List<RecommendRes> recommendations,
        boolean isLiked
    ) {
    return new BookInfoRes(
            bookDetailRes,
            aiSummary,
            aiTags,
            recommendations,
            isLiked
    );
}}
