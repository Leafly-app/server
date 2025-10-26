package com.hansung.leafly.domain.member.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseResponseCode {
    MEMBER_ID_ALREADY_EXIST("SIGNUP_409_1", 409, "이미 존재하는 아이디입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
