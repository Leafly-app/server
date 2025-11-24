package com.hansung.leafly.domain.library.web.dto;

import com.hansung.leafly.domain.library.entity.enums.LibraryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LibraryReq {
    @NotBlank(message = "책 제목이 비어 있습니다.")
    private String title;

    @NotBlank(message = "저자 정보가 비어 있습니다.")
    private String author;

    @NotBlank(message = "책 표지(썸네일) URL이 비어 있습니다.")
    private String cover;

    @NotNull(message = "서재 상태를 입력해주세요.")
    private LibraryStatus status;
}
