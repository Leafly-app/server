package com.hansung.leafly.domain.member.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReadingPurpose {
    HOBBY("취미"),
    SELF_DEVELOPMENT("자기계발"),
    LEARNING("학습"),
    WORK("업무"),
    HEALING("힐링");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}