package com.hansung.leafly.infra.aladin;

import com.hansung.leafly.domain.book.web.dto.AladinSearchRes;
import com.hansung.leafly.domain.book.web.dto.RecommendRes;
import com.hansung.leafly.infra.aladin.dto.BookRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AladinClient {

    @Value("${spring.aladin.isbn.url}")
    private String ISBN_URL;

    @Value("${spring.aladin.search.url}")
    private String SEARCH_URL;

    @Value("${spring.aladin.key}")
    private String TTB_KEY;

    @Value("${spring.aladin.bestseller.url}")
    private String BEST_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    public BookRes fetchBookByIsbn(String isbn) {
        String uri = UriComponentsBuilder.fromHttpUrl(ISBN_URL)
                .queryParam("ttbkey", TTB_KEY)
                .queryParam("itemIdType", "ISBN13")
                .queryParam("ItemId", isbn)
                .queryParam("output", "js")
                .queryParam("Version", "20131101")
                .queryParam("Cover", "Big")
                .build()
                .toUriString();

        return restTemplate.getForObject(uri, BookRes.class);
    }

    public AladinSearchRes search(String keyword) {
        String uri = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("ttbkey", TTB_KEY)
                .queryParam("Query", keyword)
                .queryParam("QueryType", "Title")
                .queryParam("SearchTarget", "Book")
                .queryParam("MaxResults", 20)
                .queryParam("output", "js")
                .queryParam("Version", "20131101")
                .build()
                .toUriString();

        return restTemplate.getForObject(uri, AladinSearchRes.class);
    }

    public AladinSearchRes searchByCategory(String categoryId) {
        String uri = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("ttbkey", TTB_KEY)
                .queryParam("Query", "*")   // 전체 검색
                .queryParam("QueryType", "Keyword")
                .queryParam("SearchTarget", "Book")
                .queryParam("CategoryId", categoryId)
                .queryParam("MaxResults", 6)
                .queryParam("output", "js")
                .queryParam("Version", "20131101")
                .build()
                .toUriString();

        return restTemplate.getForObject(uri, AladinSearchRes.class);
    }

    public AladinSearchRes bestSeller() {
        String uri = UriComponentsBuilder.fromHttpUrl(BEST_URL)
                .queryParam("ttbkey", TTB_KEY)
                .queryParam("QueryType", "Bestseller")
                .queryParam("SearchTarget", "Book")
                .queryParam("MaxResults", 10)
                .queryParam("output", "js")
                .queryParam("Version", "20131101")
                .build()
                .toUriString();

        return restTemplate.getForObject(uri, AladinSearchRes.class);
    }
}

