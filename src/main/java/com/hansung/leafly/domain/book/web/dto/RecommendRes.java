package com.hansung.leafly.domain.book.web.dto;

public record RecommendRes (
        String isbn,
        String title,
        String author,
        String cover
) {}
