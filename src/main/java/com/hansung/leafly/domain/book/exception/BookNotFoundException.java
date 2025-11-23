package com.hansung.leafly.domain.book.exception;

import com.hansung.leafly.global.exception.BaseException;

public class BookNotFoundException extends BaseException {
    public BookNotFoundException() {
        super(BookErrorCode.BOOK_NOT_FOUND);
    }
}
