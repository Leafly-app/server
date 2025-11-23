package com.hansung.leafly.infra.aladin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRes {

    @JsonProperty("item")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String title;
        private String author;
        private String pubDate;
        private String publisher;
        private String isbn;
        private String isbn13;
        private String description;
        private String cover;
        private Integer priceStandard;
        private Integer priceSales;
        private Double customerReviewRank;
        private Integer categoryId;
        private String categoryName;
    }
}
