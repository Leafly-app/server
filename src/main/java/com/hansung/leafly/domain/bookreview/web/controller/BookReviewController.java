package com.hansung.leafly.domain.bookreview.web.controller;

import com.amazonaws.Response;
import com.hansung.leafly.domain.bookreview.service.BookReviewService;
import com.hansung.leafly.domain.bookreview.web.dto.ReviewReq;
import com.hansung.leafly.global.auth.security.CustomMemberDetails;
import com.hansung.leafly.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookreviews")
@RequiredArgsConstructor
public class BookReviewController {
    private final BookReviewService bookReviewService;

    //독후감 생성
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> create(
        @AuthenticationPrincipal CustomMemberDetails memberDetails,
        @ModelAttribute @Valid ReviewReq req
    ){
        bookReviewService.create(memberDetails.getMember(), req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }

    //독후감 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<SuccessResponse<?>> delete(
            @AuthenticationPrincipal CustomMemberDetails memberDetails,
            @PathVariable Long reviewId
    ){
        bookReviewService.delete(reviewId,memberDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(null));
    }
}
