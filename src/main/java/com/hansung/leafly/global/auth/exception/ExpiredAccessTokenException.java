package com.hansung.leafly.global.auth.exception;

import com.hansung.leafly.global.exception.BaseException;

public class ExpiredAccessTokenException extends BaseException {
    public ExpiredAccessTokenException() {
        super(AuthErrorCode.TOKEN_EXPIRED);
    }
}
