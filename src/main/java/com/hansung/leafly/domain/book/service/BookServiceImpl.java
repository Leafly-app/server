package com.hansung.leafly.domain.book.service;

import com.hansung.leafly.domain.book.entity.enums.BookGenre;
import com.hansung.leafly.domain.book.exception.BookNotFoundException;
import com.hansung.leafly.domain.book.exception.FileEmptyException;
import com.hansung.leafly.domain.book.web.dto.*;
import com.hansung.leafly.domain.bookmark.repository.BookmarkRepository;
import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.infra.aladin.AladinClient;
import com.hansung.leafly.infra.aladin.dto.BookRes;
import com.hansung.leafly.infra.googlevision.GoogleVisionClient;
import com.hansung.leafly.infra.openai.OpenAiClient;
import com.hansung.leafly.infra.openai.dto.BookSummaryAiRes;
import com.hansung.leafly.infra.openai.prompt.RecommendationPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final AladinClient aladinClient;
    private final OpenAiClient openAiClient;
    private final BookmarkRepository bookmarkRepository;
    private final GoogleVisionClient googleVisionClient;

    // 검색
    @Override
    public List<SearchRes> search(String keyword, BookFilterReq req,  Member member) {
        AladinSearchRes response = aladinClient.search(keyword);

        if (response == null || response.item() == null) {
            return List.of();
        }

        //️유저의 북마크된 ISBN 목록 조회
        List<Long> bookmarkedIsbns = bookmarkRepository.findIsbnsByMemberId(member.getId());
        Set<Long> bookmarkedSet = new HashSet<>(bookmarkedIsbns);


        if (req == null || req.getGenres() == null || req.getGenres().isEmpty()) {
            return response.item().stream()
                    .map(item -> SearchRes.from(
                            item,
                            bookmarkedSet.contains(Long.parseLong(item.isbn13()))
                    ))
                    .toList();
        }

        List<BookGenre> filters = req.getGenres();

        return response.item().stream()
                .filter(item -> matchesGenre(item, filters))
                .map(item -> SearchRes.from(
                        item,
                        bookmarkedSet.contains(Long.parseLong(item.isbn13()))       // 북마크 여부 체크
                ))
                .toList();
    }

    @Override
    public BookInfoRes details(Long isbn, Member member) {
        // ISBN 기반 상세 정보 조회
        BookRes bookRes = aladinClient.fetchBookByIsbn(String.valueOf(isbn));
        if (bookRes == null ||
                bookRes.getItems() == null ||
                bookRes.getItems().isEmpty() ||
                bookRes.getItems().get(0) == null) {
            throw new BookNotFoundException();
        }

        BookDetailRes detail = BookDetailRes.from(bookRes);

        // BookSummaryPrompt 생성 → AI 요약 + 태그 생성
        String prompt = RecommendationPrompt.build(detail);
        BookSummaryAiRes summaryAi = openAiClient.summarizeBook(prompt);

        // 해당 카테고리 기반 추천 목록 검색
        String categoryId = String.valueOf(bookRes.getItems().get(0).getCategoryId());
        AladinSearchRes categorySearchRes = aladinClient.searchByCategory(categoryId);
        List<RecommendRes> recommendations = toRecommendationList(categorySearchRes);

        boolean isLiked = bookmarkRepository.existsByMember_IdAndIsbn(member.getId(), isbn);

        return BookInfoRes.of(
                detail,
                summaryAi.summary(),
                summaryAi.tags(),
                recommendations,
                isLiked
        );
    }

    @Override
    public BookInfoRes ocr(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileEmptyException();
        }
        String ocrText = googleVisionClient.detectText(file);
        log.info("[OCR] Vision API 추출 텍스트:\n{}", ocrText);
        Long isbn = extractIsbn(ocrText);
        log.info("[OCR] 추출된 ISBN: {}", isbn);

        return details(isbn);
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

    private List<RecommendRes> toRecommendationList(AladinSearchRes res) {
        return res.item().stream()
                .map(item -> new RecommendRes(
                        item.isbn13(),
                        item.title(),
                        item.author(),
                        item.cover()
                ))
                .toList();
    }

    private Long extractIsbn(String ocrText) {
        Pattern pattern = Pattern.compile("ISBN\\s*97[89][0-9\\-]{10,}");
        Matcher matcher = pattern.matcher(ocrText);

        if (matcher.find()) {
            String raw = matcher.group();
            String isbn = raw.replaceAll("[^0-9]", "");

            if (isbn.length() == 13) {
                return Long.parseLong(isbn);
            }
        }

        return null;
    }
}
