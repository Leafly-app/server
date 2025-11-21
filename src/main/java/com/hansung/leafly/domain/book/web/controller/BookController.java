package com.hansung.leafly.domain.book.web.controller;

import com.hansung.leafly.domain.book.service.BookService;
import com.hansung.leafly.domain.book.web.dto.SearchRes;
import com.hansung.leafly.global.response.SuccessResponse;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Validated
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<SearchRes>>> search(
            @RequestParam @Size(min = 2, message = "검색어는 2글자 이상이어야 합니다.")
            String keyword
    ){
        List<SearchRes> res = bookService.search(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }
}
