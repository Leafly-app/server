package com.hansung.leafly.domain.bookreview.service;

import com.hansung.leafly.domain.bookreview.web.dto.ReviewDetailsRes;
import com.hansung.leafly.domain.bookreview.web.dto.ReviewListRes;
import com.hansung.leafly.domain.bookreview.web.dto.ReviewReq;
import com.hansung.leafly.domain.bookreview.web.dto.ReviewUpdateReq;
import com.hansung.leafly.domain.member.entity.Member;

public interface BookReviewService {
    void create(Member member, ReviewReq req);

    void delete(Long reviewId, Member member);

    ReviewListRes getList(Member member);

    ReviewDetailsRes getDetails(Long reviewId,Member member);

    void update(Member member, Long reviewId, ReviewUpdateReq req);
}
