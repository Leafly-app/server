package com.hansung.leafly.domain.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("검색 테스트")
    void search() {
       // bookService.search("", null, )
    }
}