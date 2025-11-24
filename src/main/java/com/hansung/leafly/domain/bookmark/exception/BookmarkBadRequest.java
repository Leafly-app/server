package com.hansung.leafly.domain.bookmark.exception;

import com.hansung.leafly.global.exception.BaseException;
import com.hansung.leafly.global.response.code.BaseResponseCode;

public class BookmarkBadRequest extends BaseException {
    public BookmarkBadRequest() {
        super(BookmarkErrorCode.BOOKMARK_BAD_REQUEST);
    }
}
