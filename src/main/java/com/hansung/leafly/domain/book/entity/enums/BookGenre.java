package com.hansung.leafly.domain.book.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookGenre {
    FICTION("소설/시/희곡"),
    ESSAY("에세이"),
    SELF_IMPROVEMENT("자기계발"),
    SCIENCE("과학"),
    HISTORY("역사"),
    ECONOMY("경제경영"),
    ART("예술/대중문화"),
    HUMANITIES("인문학"),
    HOME("가정/요리/뷰티"),
    TRAVEL("여행"),
    HEALTH("건강/취미/레저");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
