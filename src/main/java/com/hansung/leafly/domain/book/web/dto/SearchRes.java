package com.hansung.leafly.domain.book.web.dto;

public record SearchRes (
        String isbn,
        String title,
        String author,
        String cover,
        double rating,
        boolean isLiked
){
    public static SearchRes from(AladinBookItem item, boolean isLiked) {
        return new SearchRes(
                item.isbn13(),
                item.title(),
                item.author(),
                item.cover(),
                item.customerReviewRank(),
                isLiked
        );
    }
}
