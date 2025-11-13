package com.hansung.leafly.domain.member.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class AlreadyOnboardedException extends BaseException {
    public AlreadyOnboardedException(){
        super(MemberErrorCode.MEMBER_ALREADY_ONBOARDED);
    }
}
