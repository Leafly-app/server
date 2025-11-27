package com.hansung.leafly.infra.openai.dto;

import java.util.List;

public record BookSummaryAiRes(
        List<String> summary,
        List<String> tags
) {}
