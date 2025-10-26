package com.hansung.leafly.domain.member.web.controller;

import com.hansung.leafly.domain.member.service.MemberService;
import com.hansung.leafly.domain.member.web.dto.SignUpReq;
import com.hansung.leafly.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
