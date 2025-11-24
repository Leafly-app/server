package com.hansung.leafly.domain.member.web.dto.info;

import java.util.List;

public record LikeSection(
        int likeCount,
        List<BookSimple> likeBooks
) {}
