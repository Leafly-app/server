package com.hansung.leafly.domain.book.service;

import com.hansung.leafly.domain.book.web.dto.SearchRes;

import java.util.List;

public interface BookService {
    List<SearchRes> search(String keyword);
}
