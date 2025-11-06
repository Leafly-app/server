package com.hansung.leafly.global.auth.exception;

import com.hansung.leafly.global.exception.BaseException;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super(AuthErrorCode.INVALID_TOKEN);
    }
}