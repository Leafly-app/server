package com.hansung.leafly.domain.member.service;

import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.web.dto.LoginReq;
import com.hansung.leafly.domain.member.web.dto.LoginRes;
import com.hansung.leafly.domain.member.web.dto.OnboardingReq;
import com.hansung.leafly.domain.member.web.dto.SignUpReq;
import jakarta.validation.Valid;

public interface MemberService {

    void signUp(SignUpReq signUpReq);

    LoginRes login(LoginReq loginReq);

    void onboarding(Member member, OnboardingReq req);
}
