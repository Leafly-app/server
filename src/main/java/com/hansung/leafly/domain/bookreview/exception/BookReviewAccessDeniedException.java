package com.hansung.leafly.domain.bookreview.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class BookReviewAccessDeniedException extends BaseException {
    public BookReviewAccessDeniedException() {
        super(BookReviewErrorCode.BOOK_REVIEW_ACCESS_DENIED);
    }
}
