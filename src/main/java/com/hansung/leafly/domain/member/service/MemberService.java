package com.hansung.leafly.domain.member.service;

import com.hansung.leafly.domain.member.web.dto.LoginReq;
import com.hansung.leafly.domain.member.web.dto.LoginRes;
import com.hansung.leafly.domain.member.web.dto.SignUpReq;

public interface MemberService {

    void signUp(SignUpReq signUpReq);

    LoginRes login(LoginReq loginReq);

}
