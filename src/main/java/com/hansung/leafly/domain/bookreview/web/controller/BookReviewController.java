package com.hansung.leafly.domain.bookreview.web.controller;

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

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> create(
        @AuthenticationPrincipal CustomMemberDetails memberDetails,
        @ModelAttribute @Valid ReviewReq req
    ){
        bookReviewService.create(memberDetails.getMember(), req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
}
