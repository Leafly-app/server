package com.hansung.leafly.domain.book.web.dto;

public record AladinBookItem(
        String title,               // 책 제목
        String author,              // 저자
        String publisher,           // 출판사
        String pubDate,             // 출판일 (yyyy-MM-dd)
        String description,         // 책 설명
        String isbn,                // ISBN10
        String isbn13,              // ISBN13
        String cover,               // 표지 URL
        String categoryName,        // 카테고리명 (예: 컴퓨터/IT > 소프트웨어)
        double customerReviewRank   // 알라딘 사용자 평점
) {}
