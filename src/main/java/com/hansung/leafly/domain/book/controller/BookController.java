package com.hansung.leafly.domain.book.controller;

import com.hansung.leafly.domain.book.dto.BookRes;
import com.hansung.leafly.domain.book.dto.BookReviewRes;
import com.hansung.leafly.domain.book.service.BookServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/test/book")
    public Object testBookApi() {
        String isbn = "9791162243077"; // ✅ 테스트용 ISBN
        BookRes response = bookService.fetchBookByIsbn(isbn);

        if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
            return "No data found for ISBN: " + isbn;
        }

        BookRes.Item item = response.getItems().get(0);

        BookReviewRes reviewResponse = bookService.fetchBookReviews(isbn);

        // ✅ 리뷰 문자열 구성
        StringBuilder reviewSection = new StringBuilder("\n\n🗣️ [리뷰 목록]\n");
        if (reviewResponse != null && reviewResponse.getReviews() != null && !reviewResponse.getReviews().isEmpty()) {
            for (BookReviewRes.Review r : reviewResponse.getReviews()) {
                reviewSection.append(String.format("👤 %s (★%d)\n%s\n\n",
                        r.getReviewer(), r.getRating(), r.getReviewText()));
            }
        } else {
            reviewSection.append("리뷰가 없습니다.\n");
        }

        return String.format("""
                ✅ 책 제목: %s
                ✍️ 저자: %s
                🏢 출판사: %s
                📅 출간일: %s
                💰 가격: %d원 (판매가 %d원)
                ⭐ 평점: %.1f
                📚 장르: [%d] %s
                📄 쪽수: %s쪽
                📖 요약: %s
                🖼️ 표지 URL: %s
                %s
                """,
                item.getTitle(),
                item.getAuthor(),
                item.getPublisher(),
                item.getPubDate(),
                item.getPriceStandard(),
                item.getPriceSales(),
                item.getCustomerReviewRank(),
                item.getCategoryId(),
                item.getCategoryName(),
                (item.getItemPage() != null ? item.getItemPage().toString() : "정보 없음"),
                item.getDescription(),
                item.getCover(),
                reviewSection);
    }
}
