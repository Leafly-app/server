package com.hansung.leafly.infra.googlevision.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GoogleVisionErrorCode implements BaseResponseCode {
    GOOGLE_VISION_REQUEST_FAILED("VISION_503_1", 503, "Google Vision 요청 처리 중 오류가 발생하였습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
