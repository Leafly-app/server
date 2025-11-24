package com.hansung.leafly.domain.member.web.controller;

import com.hansung.leafly.domain.member.service.MemberService;
import com.hansung.leafly.domain.member.web.dto.*;
import com.hansung.leafly.domain.recommend.web.dto.RecommendRes;
import com.hansung.leafly.global.auth.security.CustomMemberDetails;
import com.hansung.leafly.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> signup(@RequestBody @Valid SignUpReq signupReq) {
        memberService.signUp(signupReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }

    //로그인
    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<?>> login(@RequestBody @Valid LoginReq loginReq){
        LoginRes loginRes = memberService.login(loginReq);

        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(loginRes));
    }

    //회원 온보딩 저장
    @PostMapping("/onboarding")
    public ResponseEntity<SuccessResponse<?>> onboarding(
            @AuthenticationPrincipal CustomMemberDetails memberDetails,
            @RequestBody @Valid OnboardingReq req
    ){
        memberService.onboarding(memberDetails.getMember(), req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }

    //회원 정보 반환
    @GetMapping
    public ResponseEntity<SuccessResponse<MemberDetailsRes>> details(
            @AuthenticationPrincipal CustomMemberDetails memberDetails
    ){
        MemberDetailsRes res = memberService.details(memberDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }
}
