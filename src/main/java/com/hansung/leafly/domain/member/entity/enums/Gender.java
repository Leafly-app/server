package com.hansung.leafly.domain.member.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    MALE("남자"),
    FEMALE("여자"),
    OTHER("기타"),
    NONE("선택안함");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}