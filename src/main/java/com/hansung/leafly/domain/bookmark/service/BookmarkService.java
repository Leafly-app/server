package com.hansung.leafly.domain.bookmark.service;

import com.hansung.leafly.domain.bookmark.web.dto.BookmarkReq;
import com.hansung.leafly.domain.member.entity.Member;
import jakarta.validation.Valid;

public interface BookmarkService {
    boolean toggle(Member member, Long isbn, BookmarkReq req);
}
