package com.hansung.leafly.domain.recommend.service;

import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.recommend.web.dto.RecommendRes;

import java.util.List;

public interface RecommendService {
    List<RecommendRes> recommendations(Member member);
}
