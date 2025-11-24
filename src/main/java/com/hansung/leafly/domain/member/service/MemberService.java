package com.hansung.leafly.domain.member.service;

import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.web.dto.*;

public interface MemberService {

    void signUp(SignUpReq signUpReq);

    LoginRes login(LoginReq loginReq);

    void onboarding(Member member, OnboardingReq req);

    MemberDetailsRes details(Member member);
}
