package com.hansung.leafly.domain.bookreview.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hansung.leafly.domain.bookreview.entity.BookReview;
import com.hansung.leafly.domain.bookreview.entity.BookTag;
import com.hansung.leafly.domain.bookreview.entity.ReviewImage;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewDetailsRes (
        String title,
        String thumbnail,
        String author,
        List<String> tags,
        int rating,
        String reviewTitle,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createAt,
        String content,
        List<String> images
){
    public static ReviewDetailsRes from(BookReview review) {
        return new ReviewDetailsRes(
                review.getTitle(),
                review.getThumbnail(),
                review.getAuthor(),
                review.getTags().stream()
                    .map(BookTag::getTag)
                        .toList(),
                review.getRating(),
                review.getReviewTitle(),
                review.getCreatedAt(),
                review.getContent(),
                review.getImages().stream()
                        .map(ReviewImage::getImageUrl)
                        .toList()
        );
    }
}
