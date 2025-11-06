package com.hansung.leafly.domain.member.exception;

import com.hansung.leafly.global.exception.BaseException;

public class MemberNotFoundException extends BaseException {
    public MemberNotFoundException() {super(MemberErrorCode.MEMBER_NOT_FOUND);}
}
