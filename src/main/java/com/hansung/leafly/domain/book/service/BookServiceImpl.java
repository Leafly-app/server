package com.hansung.leafly.domain.book.service;

import com.hansung.leafly.domain.book.dto.BookRes;
import com.hansung.leafly.domain.book.dto.BookReviewRes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BookServiceImpl {

    private static final String API_URL = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx";
    private static final String TTB_KEY = "ttboroi20090054001"; // 🔸 여기에 본인 알라딘 TTBKey 입력!

    public BookRes fetchBookByIsbn(String isbn) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = UriComponentsBuilder.fromHttpUrl(API_URL)
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

    public BookReviewRes fetchBookReviews(String isbn) {
        //RestTemplate restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder.fromHttpUrl("https://www.aladin.co.kr/ttb/api/ItemReview.aspx")
                .queryParam("ttbkey", TTB_KEY)
                .queryParam("itemIdType", "ISBN13")
                .queryParam("ItemId", isbn)
                .queryParam("output", "js")
                .queryParam("Version", "20131101")
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        headers.add("Referer", "https://www.aladin.co.kr/");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
// ✅ 응답 원문 로그로 출력
        System.out.println("\n======================");
        System.out.println("📡 [알라딘 리뷰 API 응답]");
        System.out.println(responseBody);
        System.out.println("======================\n");
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(responseBody, BookReviewRes.class);
        } catch (Exception e) {
            System.err.println("⚠️ 리뷰 파싱 실패: " + e.getMessage());
            return null;
        }
    }
}
