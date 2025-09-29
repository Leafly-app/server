package com.hansung.leafly.global.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private final BaseResponseCode baseResponseCode;
}
