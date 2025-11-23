package com.hansung.leafly.domain.book.web.dto;

import com.hansung.leafly.infra.aladin.dto.BookRes;

public record BookDetailRes(
        String title,       //책 제목
        String author,      //책 저자
        String publisher,   //출판사
        String pubDate,     //출판일
        String description, //간략 소개
        String isbn13,      //isbn값
        String cover,       //책표지
        int priceStandard,  //원가
        int priceSales      //할인가
) {
    public static BookDetailRes from(BookRes res) {
        BookRes.Item item = res.getItems().get(0);

        return new BookDetailRes(
                item.getTitle(),
                item.getAuthor(),
                item.getPublisher(),
                item.getPubDate(),
                item.getDescription(),
                item.getIsbn13(),
                item.getCover(),
                item.getPriceStandard(),
                item.getPriceSales()
        );
    }
}
