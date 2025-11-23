package com.hansung.leafly.domain.book.web.dto;

import com.hansung.leafly.infra.aladin.dto.BookRes;

public record IsbnSearchRes (
         String title,
         String author,
         String pubDate,
         String publisher,
         String isbn,
         String isbn13,
         String description,
         String cover,
         Integer priceStandard,
         Integer priceSales,
         Double customerReviewRank,
         Integer categoryId,
         String categoryName
){
    public static IsbnSearchRes from(BookRes res) {
        BookRes.Item item = res.getItems().get(0);

        return new IsbnSearchRes(
                item.getTitle(),
                item.getAuthor(),
                item.getPubDate(),
                item.getPublisher(),
                item.getIsbn(),
                item.getIsbn13(),
                item.getDescription(),
                item.getCover(),
                item.getPriceStandard(),
                item.getPriceSales(),
                item.getCustomerReviewRank(),
                item.getCategoryId(),
                item.getCategoryName()
        );
    }

}
