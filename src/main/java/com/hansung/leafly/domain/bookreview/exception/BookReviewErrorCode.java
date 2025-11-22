package com.hansung.leafly.domain.bookreview.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookReviewErrorCode implements BaseResponseCode {
    BOOK_REVIEW_ACCESS_DENIED("BOOK_REVIEW_403_1", 403, "본인이 작성한 리뷰만 삭제할 수 있습니다."),
    BOOK_REVIEW_NOT_FOUND("BOOK_REVIEW_404_1",404,"해당 독후감을 찾을 수 없습니다." );

    private final String code;
    private final int httpStatus;
    private final String message;
}
