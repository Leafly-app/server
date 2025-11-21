package com.hansung.leafly.domain.book.web.dto;

import com.hansung.leafly.domain.book.entity.enums.BookGenre;
import lombok.Getter;

import java.util.List;

@Getter
public class BookFilterReq {
    private List<BookGenre> genres;
}
