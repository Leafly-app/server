package com.hansung.leafly.domain.member.exception;

import com.hansung.leafly.global.exception.BaseException;

public class MemberAlreadyExistException extends BaseException {
    public MemberAlreadyExistException() {
        super(MemberErrorCode.MEMBER_ID_ALREADY_EXIST);
    }
}
