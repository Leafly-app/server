package com.hansung.leafly.domain.book.service;

import com.hansung.leafly.domain.book.entity.enums.BookGenre;
import com.hansung.leafly.domain.book.web.dto.*;
import com.hansung.leafly.infra.aladin.AladinClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AladinClient aladinClient;

    // 검색
    @Override
    public List<SearchRes> search(String keyword, BookFilterReq req) {
        AladinSearchResponse response = aladinClient.search(keyword);

        if (response == null || response.item() == null) {
            return List.of();
        }

        if (req == null || req.getGenres() == null || req.getGenres().isEmpty()) {
            return response.item().stream()
                    .map(item -> SearchRes.from(item, false))
                    .toList();
        }

        List<BookGenre> filters = req.getGenres();

        return response.item().stream()
                .filter(item -> matchesGenre(item, filters))
                .map(item -> SearchRes.from(item, false))
                .toList();
    }

    //카테고리 필터링
    private boolean matchesGenre(AladinBookItem item, List<BookGenre> targetGenres) {
        String middle = extractMiddleCategory(item.categoryName());

        for (BookGenre genre : targetGenres) {
            if (middle.contains(genre.getValue())) {
                return true;
            }
        }
        return false;
    }

    //카테고리 추출 메소드
    private String extractMiddleCategory(String categoryName) {
        if (categoryName == null) return "";
        String[] tokens = categoryName.split(">");
        return tokens.length >= 2 ? tokens[1].trim() : "";
    }
}
