package com.hansung.leafly.domain.book.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRes {

    @JsonProperty("item")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

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
        private SubInfo subInfo;

        // ✅ Getter & Setter
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getPubDate() { return pubDate; }
        public String getPublisher() { return publisher; }
        public String getIsbn() { return isbn; }
        public String getIsbn13() { return isbn13; }
        public String getDescription() { return description; }
        public String getCover() { return cover; }
        public Integer getPriceStandard() { return priceStandard; }
        public Integer getPriceSales() { return priceSales; }
        public Double getCustomerReviewRank() { return customerReviewRank; }
        public Integer getCategoryId() { return categoryId; }
        public String getCategoryName() { return categoryName; }
        public void setSubInfo(SubInfo subInfo) { this.subInfo = subInfo; }

        // ✅ 책 실제 쪽수 반환 (subInfo.itemPage)
        public Integer getItemPage() {
            return (subInfo != null) ? subInfo.getItemPage() : null;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubInfo {
        private Integer itemPage; // 실제 책의 쪽수

        public Integer getItemPage() { return itemPage; }
        public void setItemPage(Integer itemPage) { this.itemPage = itemPage; }
    }
}
