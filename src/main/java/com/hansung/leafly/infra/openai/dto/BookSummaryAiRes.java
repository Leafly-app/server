package com.hansung.leafly.infra.openai.dto;

import java.util.List;

public record BookSummaryAiRes(
        String summary,
        List<String> tags
) {}
