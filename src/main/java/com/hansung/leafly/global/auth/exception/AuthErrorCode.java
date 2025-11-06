package com.hansung.leafly.global.auth.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseResponseCode {
    MISSING_TOKEN("AUTH_401_1", 401, "인증토큰이 필요합니다."),
    INVALID_TOKEN("AUTH_401_2", 401, "토큰값을 확인해주세요."),
    TOKEN_EXPIRED("AUTH_401_3", 401, "만료된 토큰입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
