package com.hansung.leafly.global.auth.exception;

import com.hansung.leafly.global.exception.BaseException;

public class MissingTokenException extends BaseException {
    public MissingTokenException() {
        super(AuthErrorCode.MISSING_TOKEN);
    }
}
