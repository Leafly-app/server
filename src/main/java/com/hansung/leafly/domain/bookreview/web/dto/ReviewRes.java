package com.hansung.leafly.domain.bookreview.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hansung.leafly.domain.bookreview.entity.BookReview;
import com.hansung.leafly.domain.bookreview.entity.BookTag;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewRes (
        Long reviewId,
        String title,
        String thumbnail,
        List<String> tags,
        int rating,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createAt
){
    public static ReviewRes from(BookReview review) {
        return new ReviewRes(
                review.getId(),
                review.getTitle(),
                review.getThumbnail(),
                review.getTags().stream()
                        .map(BookTag::getTag)
                        .toList(),
                review.getRating(),
                review.getCreatedAt()
        );
    }
}
