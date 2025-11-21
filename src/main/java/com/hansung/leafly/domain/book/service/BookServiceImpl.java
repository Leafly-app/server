package com.hansung.leafly.domain.book.service;

import com.hansung.leafly.domain.book.web.dto.AladinSearchResponse;
import com.hansung.leafly.domain.book.web.dto.BookRes;
import com.hansung.leafly.domain.book.web.dto.SearchRes;
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

    // 제목 검색
    @Override
    public List<SearchRes> search(String keyword) {
        AladinSearchResponse response = aladinClient.search(keyword);

        if (response == null || response.item() == null) {
            return List.of();
        }

        return response.item().stream()
                .map(item -> SearchRes.from(
                        item,
                        false       //좋아요 기능 구현 후 수정 필요
                ))
                .toList();
    }
}
