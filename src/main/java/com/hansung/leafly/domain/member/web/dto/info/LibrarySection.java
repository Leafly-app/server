package com.hansung.leafly.domain.member.web.dto.info;

import java.util.List;

public record LibrarySection(
        int finishedCount,
        List<BookSimple> finishedBooks,
        int wantCount,
        List<BookSimple> wantBooks
) {}
