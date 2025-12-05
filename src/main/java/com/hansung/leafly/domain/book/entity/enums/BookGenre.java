package com.hansung.leafly.domain.book.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookGenre {
    FICTION("소설/시/희곡", "1"),
    ESSAY("에세이", "55889"),
    SELF_IMPROVEMENT("자기계발", "336"),
    SCIENCE("과학", "987"),
    HISTORY("역사", "74"),
    ECONOMY("경제경영", "170"),
    ART("예술/대중문화", "517"),
    HUMANITIES("인문학", "656"),
    HOME("가정/요리/뷰티", "1230"),
    TRAVEL("여행", "1196"),
    HEALTH("건강/취미/레저", "55890"),

    ALL("전체", null);

    private final String value;      // 한글 노출 이름
    private final String categoryId; // Aladin CategoryId

    @JsonValue
    public String getValue() {
        return value;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
