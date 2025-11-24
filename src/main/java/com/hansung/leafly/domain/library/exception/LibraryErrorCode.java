package com.hansung.leafly.domain.library.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LibraryErrorCode implements BaseResponseCode {
    LIBRARY_ALREADY_EXISTS("LIBRARY_409_1", 409, "이미 내 서재에 등록된 책입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
