package com.hansung.leafly.infra.s3.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class S3RequestFailedException extends BaseException {
    public S3RequestFailedException() {
        super(S3ErrorCode.S3_REQUEST_FAILED);
    }
}
