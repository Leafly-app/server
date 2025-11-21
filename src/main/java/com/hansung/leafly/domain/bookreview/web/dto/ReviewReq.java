package com.hansung.leafly.domain.bookreview.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewReq {
    @NotNull(message = "책 제목을 입력해주세요.")
    private String title;

    @NotNull(message = "저자를 입력해주세요.")
    private String author;

    @NotNull(message = "대표 이미지(thumbnail)를 입력해주세요.")
    private String thumbnail;

    @NotNull(message = "별점을 입력해주세요.")
    private Integer rating;

    @NotNull(message = "책의 카테고리를 입력해주세요.")
    private String tags;

    @Size(max = 20, message = "리뷰 제목은 최대 20자까지 입력 가능합니다.")
    private String reviewTitle;

    @NotNull(message = "리뷰 내용을 입력해주세요.")
    @Size(min = 10, message = "리뷰 내용은 최소 10자 이상 입력해주세요.")
    private String content;

    // 이미지 최대 3개까지
    @Size(max = 3, message = "이미지는 최대 3장까지 업로드 가능합니다.")
    private List<MultipartFile> images;
}
