package com.hansung.leafly.infra.openai.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class OpenaiRequestFailed extends BaseException {
    public OpenaiRequestFailed() {
        super(OpenAiErrorCode.OPENAI_REQUEST_FAILED);
    }
}
