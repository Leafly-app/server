package com.hansung.leafly.infra.openai.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OpenAiErrorCode implements BaseResponseCode {
    OPENAI_REQUEST_FAILED("OPENAI_503_1", 503, "OpenAI 요청 처리 중 오류가 발생하였습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
