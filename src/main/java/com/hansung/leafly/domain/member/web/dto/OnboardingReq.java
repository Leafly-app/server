package com.hansung.leafly.domain.member.web.dto;

import com.hansung.leafly.domain.member.entity.enums.Gender;
import com.hansung.leafly.domain.member.entity.enums.GenreType;
import com.hansung.leafly.domain.member.entity.enums.ReadingFrequency;
import com.hansung.leafly.domain.member.entity.enums.ReadingPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class OnboardingReq {

    @NotBlank(message = "출생년도를 입력해주세요.")
    private String birthYear;

    @NotNull(message = "성별을 입력해주세요.")
    private Gender gender;

    @NotNull(message = "독서 목적을 입력해주세요.")
    private ReadingPurpose readingPurpose;

    @NotNull(message = "독서 빈도를 입력해주세요.")
    private ReadingFrequency readingFrequency;

    @NotNull(message = "관심 장르를 한 개 이상 선택해주세요.")
    private List<GenreType> favoriteGenres;
}