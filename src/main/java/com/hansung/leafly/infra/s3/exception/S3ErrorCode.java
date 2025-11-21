package com.hansung.leafly.infra.s3.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum S3ErrorCode implements BaseResponseCode {
    S3_REQUEST_FAILED("S3_503_1", 503, "S3 이미지 저장 처리 중 오류가 발생하였습니다.");

    private String code;
    private int httpStatus;
    private String message;
}
