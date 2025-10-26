package com.hansung.leafly.domain.member.service;

import com.hansung.leafly.domain.member.web.dto.SignUpReq;

public interface MemberService {

    void signUp(SignUpReq signUpReq);
}
