package com.hansung.leafly.domain.library.service;

import com.hansung.leafly.domain.library.web.dto.LibraryReq;
import com.hansung.leafly.domain.member.entity.Member;

public interface LibraryService {
    void createLibrary(Member member, String isbn, LibraryReq req);
}
