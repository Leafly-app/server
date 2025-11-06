package com.hansung.leafly.domain.member.exception;

import com.hansung.leafly.global.exception.BaseException;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException( ) {
        super(MemberErrorCode.INVALID_CREDENTIALS);}
}
