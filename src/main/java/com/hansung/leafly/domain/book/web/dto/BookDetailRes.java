package com.hansung.leafly.domain.book.web.dto;

public record BookDetailRes(
        String title,
        String author,
        String publisher,
        String pubDate,
        String description,
        String isbn13,
        String cover,
        int priceStandard,
        int priceSales,
        String categoryName
) {
    public static BookDetailRes from(IsbnSearchRes item) {
        return new BookDetailRes(
                item.title(),
                item.author(),
                item.publisher(),
                item.pubDate(),
                item.description(),
                item.isbn13(),
                item.cover(),
                item.priceStandard(),
                item.priceSales(),
                item.categoryName()
        );
    }
}
