package com.hansung.leafly.domain.recommend.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hansung.leafly.domain.book.web.dto.SearchRes;

public record RecommendRes (
        String reason,
        @JsonUnwrapped SearchRes details
)
{}
