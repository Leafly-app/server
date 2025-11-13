package com.hansung.leafly.domain.member.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReadingFrequency {
    DAILY("매일"),
    WEEKLY_2_3("주 2~3회"),
    WEEKLY("주 1회"),
    MONTHLY("월 1~2회"),
    SOMETIMES("가끔");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
