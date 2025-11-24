package com.hansung.leafly.domain.book.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class FileEmptyException extends BaseException {
    public FileEmptyException() {
        super(BookErrorCode.FILE_EMPTY);
    }
}
