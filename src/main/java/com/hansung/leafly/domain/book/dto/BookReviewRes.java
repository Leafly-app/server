package com.hansung.leafly.domain.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookReviewRes {

    @JsonProperty("reviewList")
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Review {
        private String reviewer;
        private int rating;
        private String reviewText;

        public String getReviewer() { return reviewer; }
        public int getRating() { return rating; }
        public String getReviewText() { return reviewText; }
    }
}
