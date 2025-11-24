package com.hansung.leafly.domain.library.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class LibraryAlreadyExistsException extends BaseException {
    public LibraryAlreadyExistsException() {
        super(LibraryErrorCode.LIBRARY_ALREADY_EXISTS);
    }
}
