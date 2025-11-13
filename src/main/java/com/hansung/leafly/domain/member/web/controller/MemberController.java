package com.hansung.leafly.domain.member.web.controller;

import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.service.MemberService;
import com.hansung.leafly.domain.member.web.dto.LoginReq;
import com.hansung.leafly.domain.member.web.dto.LoginRes;
import com.hansung.leafly.domain.member.web.dto.OnboardingReq;
import com.hansung.leafly.domain.member.web.dto.SignUpReq;
import com.hansung.leafly.global.auth.security.CustomMemberDetails;
import com.hansung.leafly.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
