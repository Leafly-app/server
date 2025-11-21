package com.hansung.leafly.domain.bookreview.service;

import com.hansung.leafly.domain.bookreview.web.dto.ReviewReq;
import com.hansung.leafly.domain.member.entity.Member;

public interface BookReviewService {
    void create(Member member, ReviewReq req);
}
