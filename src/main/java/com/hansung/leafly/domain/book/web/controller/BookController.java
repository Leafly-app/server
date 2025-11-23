package com.hansung.leafly.domain.book.web.controller;

import com.hansung.leafly.domain.book.service.BookService;
import com.hansung.leafly.domain.book.web.dto.BookFilterReq;
import com.hansung.leafly.domain.book.web.dto.BookInfoRes;
import com.hansung.leafly.domain.book.web.dto.SearchRes;
import com.hansung.leafly.global.response.SuccessResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Validated
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<SuccessResponse<List<SearchRes>>> search(
            @RequestParam @Size(min = 2, message = "검색어는 2글자 이상이어야 합니다.")
            String keyword,
            @RequestBody @Valid BookFilterReq req
    ){
        List<SearchRes> res = bookService.search(keyword, req);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<SuccessResponse<BookInfoRes>> details(
        @PathVariable Long isbn
    ){
        BookInfoRes res = bookService.details(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }

    @PostMapping("/ocr")
    public ResponseEntity<SuccessResponse<BookInfoRes>> ocr(
            @RequestParam("file") MultipartFile file
    ){
        BookInfoRes res = bookService.ocr(file);
        return  ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }

}
