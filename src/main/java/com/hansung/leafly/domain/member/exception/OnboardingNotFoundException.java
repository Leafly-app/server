package com.hansung.leafly.domain.member.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class OnboardingNotFoundException extends BaseException {
    public OnboardingNotFoundException() {
        super(MemberErrorCode.ONBOARDING_NOT_FOUND);
    }
}
