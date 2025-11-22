package com.hansung.leafly.domain.bookreview.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hansung.leafly.domain.bookreview.entity.BookReview;

import java.time.LocalDateTime;

public record ReviewRes (
        Long reviewId,
        String title,
        String thumbnail,
        int rating,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createAt
){
    public static ReviewRes from(BookReview review) {
        return new ReviewRes(
                review.getId(),
                review.getTitle(),
                review.getThumbnail(),
                review.getRating(),
                review.getCreatedAt()
        );
    }
}
