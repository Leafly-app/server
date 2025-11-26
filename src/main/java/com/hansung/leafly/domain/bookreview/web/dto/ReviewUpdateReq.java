package com.hansung.leafly.domain.bookreview.web.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewUpdateReq {

    private String title;
    private String author;
    private String thumbnail;
    private Integer rating;
    private String category;

    @Size(max = 20)
    private String reviewTitle;

    @Size(min = 10)
    private String content;

    @Size(max = 3)
    private List<MultipartFile> images;
}
