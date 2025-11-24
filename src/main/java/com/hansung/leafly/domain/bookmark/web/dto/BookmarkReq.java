package com.hansung.leafly.domain.bookmark.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BookmarkReq {
    @NotBlank(message = "책 제목이 비어 있습니다.")
    private String title;

    @NotBlank(message = "저자 정보가 비어 있습니다.")
    private String author;

    @NotBlank(message = "책 표지(썸네일) URL이 비어 있습니다.")
    private String cover;
}
