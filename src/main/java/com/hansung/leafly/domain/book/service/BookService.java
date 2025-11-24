package com.hansung.leafly.domain.book.service;

import com.hansung.leafly.domain.book.web.dto.BookFilterReq;
import com.hansung.leafly.domain.book.web.dto.BookInfoRes;
import com.hansung.leafly.domain.book.web.dto.SearchRes;
import com.hansung.leafly.domain.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    List<SearchRes> search(String keyword, BookFilterReq req, Member member);

    BookInfoRes details(Long isbn, Member member);

    BookInfoRes ocr(MultipartFile file, Member member);
}
