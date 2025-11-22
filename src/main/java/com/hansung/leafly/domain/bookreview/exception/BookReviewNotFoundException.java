package com.hansung.leafly.domain.bookreview.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class BookReviewNotFoundException extends BaseException {
    public BookReviewNotFoundException() {
        super(BookReviewErrorCode.BOOK_REVIEW_NOT_FOUND);
    }
}
