package com.hansung.leafly.domain.member.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseResponseCode {
    INVALID_CREDENTIALS("MEMBER_401_1", 401, "아이디 또는 비밀번호를 다시 확인해주세요"),
    MEMBER_NOT_FOUND("MEMBER_404_1",404,"존재하지 않는 사용자입니다."),
    MEMBER_ID_ALREADY_EXIST("SIGNUP_409_1", 409, "이미 존재하는 아이디입니다."),
    MEMBER_ALREADY_ONBOARDED("MEMBER_409_2", 409, "이미 온보딩 정보를 등록한 회원입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
