package com.hansung.leafly.domain.recommend.web.controller;

import com.hansung.leafly.domain.recommend.service.RecommendService;
import com.hansung.leafly.domain.recommend.web.dto.RecommendRes;
import com.hansung.leafly.global.auth.security.CustomMemberDetails;
import com.hansung.leafly.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendController {
    private final RecommendService recommendService;

    //온보딩 기반 책 추천
    @GetMapping
    public ResponseEntity<SuccessResponse<List<RecommendRes>>> recommendations(
            @AuthenticationPrincipal CustomMemberDetails memberDetails
    ){
        List<RecommendRes> res = recommendService.recommendations(memberDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }
}
