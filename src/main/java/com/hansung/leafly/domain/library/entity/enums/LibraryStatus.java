package com.hansung.leafly.domain.library.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LibraryStatus {
    WANT_TO_READ("읽고 싶음"),
    DONE("완독");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
