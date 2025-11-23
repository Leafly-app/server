package com.hansung.leafly.domain.book.web.dto;

import java.util.List;

public record AladinSearchRes(
        List<AladinBookItem> item
) {}

