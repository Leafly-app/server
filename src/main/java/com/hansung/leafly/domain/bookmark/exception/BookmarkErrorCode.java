package com.hansung.leafly.domain.bookmark.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookmarkErrorCode implements BaseResponseCode {
    BOOKMARK_BAD_REQUEST("BOOKMARK_400_1", 400, "북마크 등록 시 필요한 책 정보가 누락되었습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
