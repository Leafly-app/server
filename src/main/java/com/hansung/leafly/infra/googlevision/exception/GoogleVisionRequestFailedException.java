package com.hansung.leafly.infra.googlevision.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class GoogleVisionRequestFailedException extends BaseException {
    public GoogleVisionRequestFailedException() {
        super(GoogleVisionErrorCode.GOOGLE_VISION_REQUEST_FAILED);
    }
}
