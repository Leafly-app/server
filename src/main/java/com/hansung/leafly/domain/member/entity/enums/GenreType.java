package com.hansung.leafly.domain.member.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GenreType {
    NOVEL("소설"),
    ESSAY("에세이"),
    SELF_IMPROVEMENT("자기계발"),
    SCIENCE("과학"),
    HISTORY("역사"),
    ECONOMY("경제"),
    ART("예술"),
    PHILOSOPHY("철학"),
    PSYCHOLOGY("심리학"),
    COOKING("요리"),
    HEALTH("건강"),
    TRAVEL("여행");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}