package com.hansung.leafly.domain.book.web.dto;

public record SearchRes (
        String isbn,
        String title,
        String author,
        String cover,
        String category,
        double rating,
        boolean isLiked
){
    public static SearchRes from(AladinBookItem item, boolean isLiked) {
        return new SearchRes(
                item.isbn13(),
                item.title(),
                item.author(),
                item.cover(),
                item.categoryName(),
                item.customerReviewRank(),
                isLiked
        );
    }
}
