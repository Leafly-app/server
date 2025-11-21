package com.hansung.leafly.infra.openai.dto;

import java.util.List;

public record RecommendAiRes(
        List<RecommendedAiDetails> books
) {}


