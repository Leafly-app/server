package com.hansung.leafly.domain.bookreview.service;

import com.hansung.leafly.domain.bookreview.entity.BookReview;
import com.hansung.leafly.domain.bookreview.entity.BookTag;
import com.hansung.leafly.domain.bookreview.entity.ReviewImage;
import com.hansung.leafly.domain.bookreview.exception.BookReviewAccessDeniedException;
import com.hansung.leafly.domain.bookreview.exception.BookReviewNotFoundException;
import com.hansung.leafly.domain.bookreview.repository.BookReviewRepository;
import com.hansung.leafly.domain.bookreview.repository.BookTagRepository;
import com.hansung.leafly.domain.bookreview.repository.ReviewImageRepository;
import com.hansung.leafly.domain.bookreview.web.dto.ReviewListRes;
import com.hansung.leafly.domain.bookreview.web.dto.ReviewReq;
import com.hansung.leafly.domain.bookreview.web.dto.ReviewRes;
import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.infra.s3.S3Service;
import com.hansung.leafly.infra.s3.exception.S3RequestFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {
    private final BookReviewRepository bookReviewRepository;
    private final BookTagRepository bookTagRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
    public void create(Member member, ReviewReq req) {
        BookReview bookReview = BookReview.toEntity(member, req);
        bookReviewRepository.save(bookReview);

        List<BookTag> tags = processTags(req.getCategory(), bookReview);
        bookTagRepository.saveAll(tags);

        List<ReviewImage> images = processImages(req.getImages(), bookReview);
        reviewImageRepository.saveAll(images);
    }

    @Override
    @Transactional
    public void delete(Long reviewId, Member member) {
        BookReview bookReview = bookReviewRepository.findById(reviewId)
                .orElseThrow(()-> new BookReviewNotFoundException());

        if(!bookReview.getMember().getId().equals(member.getId())) {
            throw new BookReviewAccessDeniedException();
        }

        bookReviewRepository.delete(bookReview);
    }

    @Override
    public ReviewListRes getList(Member member) {
        List<BookReview> reviews = bookReviewRepository.findAllByMember(member);

        List<ReviewRes> reviewResList = reviews.stream()
                .map(ReviewRes::from)
                .toList();

        return new ReviewListRes(reviewResList.size(), reviewResList);
    }

    // 카테고리 태그화
    private List<BookTag> processTags(String rawTags, BookReview review) {
        if (rawTags == null || rawTags.isEmpty()) {
            return List.of();
        }

        List<BookTag> result = new ArrayList<>();

        // "컴퓨터과학>알고리즘>자료구조"
        String[] parts = rawTags.split(">");

        if (parts.length <= 1) {
            return List.of();
        }

        for (int i = 1; i < parts.length; i++) {
            String tagName = parts[i].trim();

            if (tagName.isEmpty()) continue;

            BookTag tag = BookTag.builder()
                    .bookReview(review)
                    .tag("#" + tagName)
                    .build();

            result.add(tag);
        }

        return result;
    }

    // 이미지 url 생성
    private List<ReviewImage> processImages(List<MultipartFile> images, BookReview review) {

        if (images == null || images.isEmpty()) {
            return List.of();
        }

        List<ReviewImage> result = new ArrayList<>();

        for (MultipartFile image : images) {
            if (image.isEmpty()) continue;

            try {
                String imageUrl = s3Service.uploadImage(image);

                ReviewImage entity = ReviewImage.builder()
                        .bookReview(review)
                        .imageUrl(imageUrl)
                        .build();

                result.add(entity);

            } catch (IOException e) {
                throw new S3RequestFailedException();
            }
        }

        return result;
    }

}
