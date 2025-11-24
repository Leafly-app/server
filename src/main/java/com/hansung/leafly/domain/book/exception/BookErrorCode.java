package com.hansung.leafly.domain.book.exception;

import com.hansung.leafly.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum BookErrorCode implements BaseResponseCode{
    FILE_EMPTY("FILE_400_1", 400, "업로드된 파일이 비어있습니다."),
    BOOK_NOT_FOUND("BOOK_404_1", 404, "해당 ISBN으로 검색된 책이 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
